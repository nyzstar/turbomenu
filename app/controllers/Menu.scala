package controllers

import play.api.mvc.{Action, Controller, Flash}
import models.MenuItem
import models.Restaurant
import models.Rating
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText, of, number,text}
import play.api.i18n.Messages
import play.api.data.format.Formats._
import collection.immutable.List

object MenuControl extends Controller{

	case class RatingData(userId: Long, value: Int)

	private val itemForm: Form[MenuItem] = Form(
		mapping(
			"id" -> longNumber.verifying(
				"validation.id.duplicate", MenuItem.findById(_).isEmpty),
			"name" -> nonEmptyText,
			"category_id" -> of[Long],
			"price" -> of[Float],
			"restaurant_id" -> of[Long],
			"imageUrl" -> text.verifying("Enter imageurl", {!_.isEmpty})
		)(MenuItem.apply)(MenuItem.unapply)
	)

	// val ratingForm: Form[Rating] = Form( 
	// 	mapping(
	// 	"id" ->longNumber.verifying(
	// 			"validation.id.duplicate", Rating.findById(_).isEmpty),
	// 	"value" -> of[Int],
	// 	"timeModified" -> of[Long],
	// 	"userId" -> of[Long],
	// 	"menuItemId" -> of[Long]
	// 	)(Rating.apply)(Rating.unapply)
	// )

	val ratingForm = Form{
		mapping(
			"userId" -> of[Long],
			"value" -> of[Int]
		)(RatingData.apply)(RatingData.unapply)
	}


	def list = Action { implicit request =>
		val items = MenuItem.findAll
		Ok(views.html.items.list(items))
	}

	def show(id: Long) = Action { implicit request =>
		val item = MenuItem.findById(id).get
		val restaurant = Restaurant.findById(item.restaurantId).get
		val rating = Rating.averageRatingPerItem(item.id)

		Ok(views.html.items.details(item, restaurant, rating, ratingForm))
	}

	def newRating(id: Long) = Action { implicit request =>
		val newRate = ratingForm.bindFromRequest()

		newRate.fold(
			hasErrors = { form =>
				Redirect(routes.MenuControl.show(id)).
					flashing(Flash(form.data) + 
						("error" -> Messages("validation.errors")))
			},

			success = { newItem =>
				val ratingList = Rating.findByMenuItemAndUser(id, newItem.userId)
				
				// if this user has not rated this item yet
				if (ratingList.length == 0){
					println("insert!")
					Rating.insert(Rating(0, newItem.value, 191919191L, newItem.userId, id))
				
				// if this user wants to update a rate
				} else {
					Rating.update(Rating(ratingList(0).id, newItem.value, 191919191L, newItem.userId, id))
				}
				val message = Messages("rating.new.success", newItem.value)
				Redirect(routes.MenuControl.show(id)).flashing("success" -> message)
			}
		) 
	} 

	def newItem = Action{ implicit request =>
		val form = if (request.flash.get("error").isDefined)
			itemForm.bind(request.flash.data)
		else
			itemForm

		Ok(views.html.items.editMenu(form))
	}

	// def upload = Action(parse.multipartFormData) { implicit request =>
	//   request.body.file("picture").map { picture =>
	//     import java.io.File
	//     val filename = picture.filename 
	//     val contentType = picture.contentType
	//     picture.ref.moveTo(new File("test_data/img/" + filename))
	//     Ok("File uploaded")
	//   }.getOrElse {
	//     Redirect(routes.Application.index).flashing(
	//       "error" -> "Missing file"
	//     )
	//   }
	// }

	def save = Action(parse.multipartFormData){ implicit request =>
		request.body.file("picture").map { picture =>
		import java.io.File
		val filename = picture.filename 
		val contentType = picture.contentType
		picture.ref.moveTo(new File(s"/public/test_data/img/" + filename))
		}

		val newMenu = itemForm.bindFromRequest()

		newMenu.fold(
			
			hasErrors = { form =>
				Redirect(routes.MenuControl.newItem()).
					flashing(Flash(form.data) + 
						("error" -> form.errors.mkString))
			},

			success = { newItem =>
				//newItem.imageUrl="/test_data/img/"+filename
				MenuItem.insert(newItem)

				val message = Messages("menu.new.success", newItem.name)
				Redirect(routes.MenuControl.show(newItem.id)).flashing("success" -> message)
			}
		)
	}
}