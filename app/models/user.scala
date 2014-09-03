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

	def insert(user: User): User = inTransaction{
		userTable.insert(user)
	}

	def update(user: User) = inTransaction{
		userTable.update(user)
	}

	def delete(user: User) = inTransaction{
		userTable.deleteWhere(u => u.id === user.id)
	}

	def findByEmail(email: String):Option[User] = inTransaction{
		queryByEmail(email).toList.headOption
	}

	def findById(id: Long) = inTransaction {
		queryById(id).toList.headOption
	}

	def findAll:Iterable[User] = inTransaction {
		allQ.toList
	}

	/**
	* Authenticate a User.
	*/
	def authenticate(email: String, password: String): Option[User] = {
		findByEmail(email) match {
			case Some(user) if user.checkPassword(password) => Some(user)
			case None => None
		}
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

	def queryByEmail(email: String): Query[User] = from(userTable){
		user => where(user.email === email) select(user)
	}
	
}