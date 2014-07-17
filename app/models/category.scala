package models
import org.squeryl.KeyedEntity
import org.squeryl.dsl.OneToMany

case class Category(
	id: Long, 
	name: String) extends KeyedEntity[Long]{
	lazy val menu: OneToMany[MenuItem] =
		Database.categoryToMenu.left(this)
}

/*
object Category{
	var items = Set(
		Category(0L, "Appetizer"),
		Category(1L, "Entree"),
		Category(2L, "Dessert"),
		Category(3L, "Drink")
	)

	def findAll = items.toList.sortBy(_.id)
	
	def findNameById(id: Long) = items.find(_.id == id).name

}

*/