package controllers

import play.api.mvc._
import models.User
import play.api.data.Form
import play.api.data.Forms.{mapping, ignored, nonEmptyText, tuple}
import play.api.i18n.Messages
import play.api.data.format.Formats._

object Auth extends Controller{
	val loginForm = Form(
		tuple(
			"email" -> nonEmptyText,
			"password" -> nonEmptyText
		) verifying (Messages("login.errors"), result => result match{
			case (email, password) => User.authenticate(email, password).isDefined
		})
	)

	// def check(username: String, password: String) = {
	// 	User.findByEmail(username) match{
	// 		case Some(u) => u.checkPassword(password)
	// 		case None => false
	// 	}
	// }

	def login = Action { implicit request => 
		Ok(views.html.login(loginForm))
	}

	def authenticate = Action { implicit request =>
		loginForm.bindFromRequest.fold(
			hasErrors = { form =>
				Redirect(routes.HomeControl.browse()).
					flashing(("error" -> Messages("login.errors")))
			},

			success = { user =>
				Redirect(routes.HomeControl.browse()).withSession(Security.username -> user._1)
			}
		)
	}

	def logout = Action {
		Redirect(routes.Auth.login).withNewSession.flashing(
			"Success" -> "You are now logged out."
		)
	}
}

trait Secured {
	def username(request: RequestHeader) = request.session.get(Security.username)
	
	def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

	def IsAuthenticated(f: => String => Request[AnyContent] => Result) = {
		Security.Authenticated(username, onUnauthorized) {
			user => Action(request => f(user)(request))
		}
	}

	def withUser(f: User => Request[AnyContent] => Result) = IsAuthenticated {
		username => implicit request => 
		User.findByEmail(username).map{ user => 
			f(user)(request)
		}.getOrElse(onUnauthorized(request))
	}
}