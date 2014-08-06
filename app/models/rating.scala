package models
import org.squeryl.{KeyedEntity, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import collection.Iterable

case class Rating(
	id:	Long,
	value:	Int,
	timeModified: Long,
	userId: Long,
	menuItemId: Long
) extends KeyedEntity[Long]

object Rating{
	import Database.{ratingTable}

	def insert(rating: Rating): Rating = inTransaction{
		ratingTable.insert(rating)
	}

	def update(rating: Rating) = {
		ratingTable.update(rating)
	}

	def updateRating(id: Long, newRating: Int) = {

	}

	def delete(rating: Rating) = {
		ratingTable.deleteWhere(r => r.id === rating.id)
	}

	def findById(id: Long) = inTransaction {
		queryById(id).toList.headOption
	}

	def findByMenuItemAndUser(menuItemId: Long, userId: Long) = inTransaction {
		queryByMenuItemAndUser(menuItemId, userId).toList
	}

	def averageRatingPerItem(menuItemId: Long) = inTransaction {
		val list = queryByMenuItemId(menuItemId).toList
		list.map(_.value).sum / list.length
	}

	def findAll: Iterable[Rating] = inTransaction {
		allQ.toList
	}

	/**
	Helper Function
	**/
	def allQ: Query[Rating] = from(ratingTable){
		rating => select(rating)
	}

	def queryById(id: Long): Query[Rating] = from(ratingTable){
		rating => where(rating.id === id) select(rating)
	}

	def queryByMenuItemId(id: Long): Query[Rating] = from(ratingTable){
		rating=> where(rating.menuItemId === id) select(rating)
	}

	def queryByMenuItemAndUser(menuItemId: Long, userId: Long): Query[Rating] = from(ratingTable){
		rating=> where(rating.menuItemId === menuItemId and rating.userId === userId) select(rating)
	}


}