package magicpricegerbil

import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.atmosphere.cpr.AtmosphereHandler

import com.threetaps.client.ThreetapsClient
import com.threetaps.model.Message
import com.threetaps.model.Posting

class GameController {

	ThreeTapsPostService threeTapsPostService
	
	def startover() {
		clearPost()
		redirect(action: 'index')
	}
	
	def guess() {
		Integer guess = params.guess != null && params.guess.isFloat() ? new Float(params.float('guess')).round() : null

		log.debug 'Guess is ' + guess
		
		Posting post = getPost()
		
		Integer price = post.price.round()
		boolean correct = false
		boolean higher = false
		
		if (price == guess)  {
			correct = true
			clearPost()
		} else {
			higher = price > guess
		}
		
		render(contentType: "text/json") {[
				correct: correct,
				higher: higher,
				actualPrice: ('$'+ NumberFormat.getInstance().format(price)),
				formattedPrice: ('$'+ NumberFormat.getInstance().format(guess))
			]
		}
	}
	
    def index() {
		//println threeTapsPostService.status

		Posting post = getPost()
		String heading = post.heading
		return [post: post, heading: heading]
    }
	
	private Posting getPost() {
		if (!session.post) {
			setupGame()
		}
		Posting post = session.post
	}
	
	private void setupGame() {
		Posting post = threeTapsPostService.getSearchResults( 'couch' )
		session.post = post
	}
	
	private void clearPost() {
		session.post = null
	}
	
}
