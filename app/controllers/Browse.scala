package controllers

import play.api.mvc.{Action, Controller, Flash}
import models.MenuItem
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText, of}
import play.api.i18n.Messages
import play.api.data.format.Formats._
import collection.immutable.List

object BrowseControl extends Controller with Secured{

	//most popular with user login
	def index = Action {implicit request => {
			val items = MenuItem.findAll.slice(0, 6)
			// Ok(views.html.home(items, items))
			username(request).map { user =>
	        	
	        	//Get the recommended items.
	        	//val rmd_items = getRecommendedItems()
	        	
	        	//Get the new items.
	        	//val new_items = getNewItems()

				Ok(views.html.home(items, items))
	      	}.getOrElse {
	      		//Get the popular items.
	      		//val pop_items = getPopularItems()
	      		//Get the new items.
	      		//val new_items = getNewItems()

	      		Ok(views.html.home(items, items))
	      	}
		}
	}

	def browse(sortBy: String, sortDirection: String, searchBy: String) = Action { implicit request => 
		{
			val items = MenuItem.findAll
			Ok(views.html.browse(items))
		}
	}

	//simple search logic.
	def filterBy(items: Iterable[MenuItem], 
		query: String): Iterable[MenuItem] = {
		items
	}

}