package models
import org.squeryl.{Schema, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.{OneToMany, ManyToOne}

object Database extends Schema {
	val menuTable: Table[MenuItem] = table[MenuItem]("menu")
	val restaurantsTable: Table[Restaurant] = table[Restaurant]("restaurant")
	val categoryTable: Table[Category] = table[Category]("category")

	val restaurantToMenu = 
		oneToManyRelation(restaurantsTable, menuTable).via(
			(r, m) => r.id === m.restaurantId)

	val categoryToMenu =
		oneToManyRelation(categoryTable, menuTable).via(
			(c, m) => c.id === m.category_id)

/*
	on(menuTable) { m => declare{
		m.id is(autoIncremented)
	}}

	on(restaurantsTable){ r => declare{
		r.id is(autoIncremented)
	}}

	on(categoryTable){ c => declare{
		c.id is(autoIncremented)
	}}

*/

}