package models
import org.squeryl.dsl.OneToMany
import org.squeryl.{KeyedEntity, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import collection.Iterable

case class Restaurant(
	id: Long,
	name: String,
	address: String,
	open: Int,
	close: Int,
	contact: String) extends KeyedEntity[Long]{
	lazy val menu: OneToMany[MenuItem] = 
		Database.restaurantToMenu.left(this)
}

object Restaurant{
	import Database.{restaurantTable}

	def insert(restaurant: Restaurant): Restaurant = {
		restaurantTable.insert(restaurant)
	}

	def update(restaurant: Restaurant) = {
		restaurantTable.update(restaurant)
	}

	def delete(restaurant: Restaurant) = {
		restaurantTable.deleteWhere(r => r.id === restaurant.id)
	}

	def findById(id: Long) = inTransaction {
		queryById(id).toList.headOption
	}

	def findAll: Iterable[Restaurant] = inTransaction {
		allQ.toList
	}

	/**
	Helper Function
	**/
	def allQ: Query[Restaurant] = from(restaurantTable){
		restaurant => select(restaurant)
	}

	def queryById(id: Long): Query[Restaurant] = from(restaurantTable){
		restaurant => where(restaurant.id === id) select(restaurant)
	}
}