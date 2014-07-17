package models
import org.squeryl.KeyedEntity
import org.squeryl.dsl.OneToMany

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