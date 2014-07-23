package controllers

import play.api.mvc.{Action, Controller, Flash}
import models.MenuItem
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText, of}
import play.api.i18n.Messages
import play.api.data.format.Formats._
import collection.immutable.List

object Home extends Controller{

	def browse(	orderBy: String, 
				sortDirection: String, 
				searchBy: String) = Action{	implicit request =>
		val items = MenuItem.findAll
		Ok(views.html.browse(items))
	}

}