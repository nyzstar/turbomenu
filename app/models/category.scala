package models
import org.squeryl.dsl.OneToMany
import org.squeryl.{KeyedEntity, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import collection.Iterable

case class Category(
	id: Long, 
	name: String) extends KeyedEntity[Long]{
	lazy val menu: OneToMany[MenuItem] =
		Database.categoryToMenu.left(this)
}

object Category{
	import Database.{categoryTable}

	def insert(category: Category): Category = inTransaction {
		categoryTable.insert(category)
	}

	def update(category: Category) = inTransaction {
		categoryTable.update(category)
	}

	def delete(category: Category) = inTransaction {
		categoryTable.deleteWhere(c => c.id === category.id)
	}

	def findById(id: Long) = inTransaction {
		queryById(id).toList.headOption
	}

	def findAll: Iterable[Category] = inTransaction {
		allQ.toList
	}

	/**
	Helper Function
	**/
	def allQ: Query[Category] = from(categoryTable){
		category => select(category)
	}

	def queryById(id: Long): Query[Category] = from(categoryTable){
		category => where(category.id === id) select(category)
	}
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