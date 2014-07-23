package models
import org.squeryl.{Schema, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.{OneToMany, ManyToOne}

object Database extends Schema {
	val menuTable: Table[MenuItem] = table[MenuItem]("menu")
	val restaurantTable: Table[Restaurant] = table[Restaurant]("restaurant")
	val categoryTable: Table[Category] = table[Category]("category")
	val ratingTable: Table[Rating] = table[Rating]("rating")
	val userTable: Table[User] = table[User]("user")

	val restaurantToMenu = 
		oneToManyRelation(restaurantTable, menuTable).via(
			(r, m) => r.id === m.restaurantId)

	val categoryToMenu =
		oneToManyRelation(categoryTable, menuTable).via(
			(c, m) => c.id === m.categoryId)

/*
	on(menuTable) { m => declare{
		m.id is(autoIncremented)
	}}

	on(restaurantTable){ r => declare{
		r.id is(autoIncremented)
	}}

	on(categoryTable){ c => declare{
		c.id is(autoIncremented)
	}}

*/

}