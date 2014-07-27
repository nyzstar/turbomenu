package controllers

import play.api.mvc.{Action, Controller, Flash}
import models.MenuItem
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText, of}
import play.api.i18n.Messages
import play.api.data.format.Formats._
import collection.immutable.List

object HomeControl extends Controller{

	def browse(	orderBy: String, 
				sortDirection: String, 
				searchBy: String) = Action{	implicit request =>
		val items = filterBy(MenuItem.findAll, searchBy)
		Ok(views.html.browse(items))
	}

	//simple search logic.
	def filterBy(items: Iterable[MenuItem], 
		query: String): Iterable[MenuItem] = {
		items
	}

}