package models
import org.squeryl.dsl.ManyToOne
import org.squeryl.{KeyedEntity, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import collection.Iterable

case class MenuItem(
	id: Long, 
	name: String, 
	categoryId: Long, 
	price: Float, 
	restaurantId: Long,
	imageUrl: String) extends KeyedEntity[Long]{
	lazy val restaurant: ManyToOne[Restaurant] 
		= Database.restaurantToMenu.right(this)
	lazy val category: ManyToOne[Category]
		= Database.categoryToMenu.right(this)
}

object MenuItem {
	import Database.{menuTable}

	def insert(menuItem: MenuItem): MenuItem = inTransaction{
		//prevent menuItem from changing after insertion.
		//val defensiveCopy = menuItem copy()
		menuTable.insert(menuItem)
	}

	def update(menuItem: MenuItem) = inTransaction {
		menuTable.update(menuItem)
	}

	def delete(menuItem: MenuItem) = inTransaction {
		menuTable.deleteWhere(m => m.id === menuItem.id)
	}

	def findById(id: Long) = inTransaction {
		queryById(id).toList.headOption
	}

	def findAll: Iterable[MenuItem] = inTransaction {
		allQ.toList
	}

	/**
	Helper Function
	**/
	def allQ: Query[MenuItem] = from(menuTable){
		menuItem => select(menuItem)
	}

	def queryById(id: Long): Query[MenuItem] = from(menuTable){
		menuItem => where(menuItem.id === id) select(menuItem)
	}
}
/*
object MenuItem {
	var items = Set(
		MenuItem(0L, "Fried Chicken", 2L, 10.20f),
		MenuItem(1L, "Beef Broccoli", 1L, 9.00f),
		MenuItem(2L, "Salad", 0L, 5.60f)
	)

	def findAll = items.toList.sortBy(_.id)
	
	def findById(id: Long) = items.find(_.id == id)

	def add(item: MenuItem){
		items = items + item
	}
}

*/