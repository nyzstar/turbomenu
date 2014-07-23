package models
import org.squeryl.{KeyedEntity, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import collection.Iterable

case class Rating(
	id:	Long,
	value:	Int,
	timeModifed: Long,
	userId: Long,
	menuItemId: Long
) extends KeyedEntity[Long]

object Rating{
	import Database.{ratingTable}

	def insert(rating: Rating): Rating = {
		ratingTable.insert(rating)
	}

	def update(rating: Rating) = {
		ratingTable.update(rating)
	}

	def delete(rating: Rating) = {
		ratingTable.deleteWhere(r => r.id === rating.id)
	}

	def findById(id: Long) = inTransaction {
		queryById(id).toList.headOption
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
}