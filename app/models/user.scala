package models
import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne
import org.squeryl.{KeyedEntity, Query, Table}
import org.squeryl.PrimitiveTypeMode._
import collection.Iterable

case class User(
	id:					Long,
	email:				String,
	password:			String
) extends KeyedEntity[Long]{
	def checkPassword(password: String): Boolean = 
		this.password == password
}

object User{
	import Database.{userTable}

	def insert(user: User): User = {
		userTable.insert(user)
	}

	def update(user: User) = {
		userTable.update(user)
	}

	def delete(user: User) = {
		userTable.deleteWhere(u => u.id === user.id)
	}

	def findById(id: Long) = inTransaction {
		queryById(id).toList.headOption
	}

	def findAll: Iterable[User] = inTransaction {
		allQ.toList
	}

	/**
	Helper Function
	**/
	def allQ: Query[User] = from(userTable){
		user => select(user)
	}

	def queryById(id: Long): Query[User] = from(userTable){
		user => where(user.id === id) select(user)
	}

}