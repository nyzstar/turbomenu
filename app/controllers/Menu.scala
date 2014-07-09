package controllers

import play.api.mvc.{Action, Controller, Flash}
import models.MenuItem
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText, of}
import play.api.i18n.Messages
import play.api.data.format.Formats._

object Menu extends Controller{

	private val itemForm: Form[MenuItem] = Form(
		mapping(
			"id" -> longNumber.verifying(
				"validation.id.duplicate", MenuItem.findById(_).isEmpty),
			"name" -> nonEmptyText,
			"category" -> nonEmptyText,
			"price" -> of[Float]
		)(MenuItem.apply)(MenuItem.unapply)
	)

	def list = Action { implicit request =>
		val items = MenuItem.findAll
		Ok(views.html.items.list(items))
	}

	def show(id: Long) = Action { implicit request =>
		MenuItem.findById(id).map{ item => 
			Ok(views.html.items.details(item))
		}.getOrElse(NotFound)
	}

	def newItem = Action{ implicit request =>
		val form = if (request.flash.get("error").isDefined)
			itemForm.bind(request.flash.data)
		else
			itemForm

		Ok(views.html.items.editMenu(form))
	}

	def save = Action{ implicit request =>
		val newMenu = itemForm.bindFromRequest()

		newMenu.fold(
			
			hasErrors = { form =>
				Redirect(routes.Menu.newItem()).
					flashing(Flash(form.data) + 
						("error" -> Messages("validation.errors")))
			},

			success = { newItem =>
				MenuItem.add(newItem)
				val message = Messages("menu.new.success", newItem.name)
				Redirect(routes.Menu.show(newItem.id)).flashing("success" -> message)
			}

		)
	}
}