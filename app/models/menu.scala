package models

case class MenuItem(
	id: Long, name: String, category: String, price: Float)

object MenuItem {
	var items = Set(
		MenuItem(1001L, "Fried Chicken", "Snack", 10.20f),
		MenuItem(1002L, "Beef Broccoli", "Entree", 9.00f),
		MenuItem(1003L, "Salad", "Appetizer", 5.60f)
	)

	def findAll = items.toList.sortBy(_.id)
	
	def findById(id: Long) = items.find(_.id == id)

	def add(item: MenuItem){
		items = items + item
	}
}