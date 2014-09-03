package controllers

import play.api.mvc.{Action, Controller, Flash}
import models.Restaurant
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText, of}
import play.api.i18n.Messages
import play.api.data.format.Formats._
import collection.immutable.List

object RestaurantControl extends Controller{

	private val restaurantForm: Form[Restaurant] = Form(
		mapping(
			"id" -> longNumber.verifying(
				"validation.id.duplicate", Restaurant.findById(_).isEmpty),
			"name" -> nonEmptyText,
			"address" -> nonEmptyText,
			"open" -> of[Int],
			"close" -> of[Int],
			"contact" -> nonEmptyText
		)(Restaurant.apply)(Restaurant.unapply)
	)

	def list = Action { implicit request =>
		val restaurants = Restaurant.findAll
		Ok(views.html.restaurants.list(restaurants))
	}

	def show(id: Long) = Action { implicit request =>
		Restaurant.findById(id).map{ restaurant => 
			Ok(views.html.restaurants.details(restaurant))
		}.getOrElse(NotFound)
	}

	def newRestaurant = Action{ implicit request =>
		val form = if (request.flash.get("error").isDefined)
			restaurantForm.bind(request.flash.data)
		else
			restaurantForm

		Ok(views.html.restaurants.editRestaurant(form))
	}

	def save = Action{ implicit request =>
		val newRestaurant = restaurantForm.bindFromRequest()

		newRestaurant.fold(
			
			hasErrors = { form =>
				Redirect(routes.RestaurantControl.newRestaurant()).
					flashing(Flash(form.data) + 
						("error" -> Messages("validation.errors")))
			},

			success = { newRestaurant =>
				Restaurant.insert(newRestaurant)
				val message = Messages("restaurant.new.success", newRestaurant.name)
				Redirect(routes.RestaurantControl.show(newRestaurant.id)).flashing("success" -> message)
			}

		)
	}

}