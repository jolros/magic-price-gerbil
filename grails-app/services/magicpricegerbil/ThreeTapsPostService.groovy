package magicpricegerbil

import com.threetaps.client.ThreetapsClient
import com.threetaps.dto.search.SearchRequest
import com.threetaps.model.Posting
import com.threetaps.dto.search.SearchResponse
import com.threetaps.model.Message
import com.threetaps.model.Posting

import org.codehaus.groovy.grails.commons.GrailsApplication

class ThreeTapsPostService {
	
	GrailsApplication grailsApplication
	
	ThreetapsClient client
	
	ThreeTapsPostService() {
		String apiKey = grailsApplication?.config?.threetaps?.apikey ?: 'u69aea8pxqh3bbeuewpyhd8h'
		client = ThreetapsClient.getInstance().setAuthID(apiKey)
	}
	
	String getStatus() {
		Message systemMessage = client.statusClient.system()
		return systemMessage.message
	}
	
	Posting getSearchResults(String term) {
		log.debug "Searching for: ${term}"
		
		SearchRequest request = new SearchRequest()
		request.image = true
		request.text = term
		request.price = '>2'
		request.location = 'SAC'
		//request.status = 'offered'
		request.category = 'SSSS'
		SearchResponse response
		
		try {
			response = client.searchClient.search(request)
		} catch(IOException e) {
			log.error( 'Could not preform search', e)
		}
		
		log.debug "Response: ${response.success}"
		
		if (response && response.success && response.results) {
			if (false) {
				response.results.each { Posting posting ->
					log.debug "${posting.heading} for ${posting.price}"
					if (posting.images) posting.images.each { log.debug ('  ' + it) }
				}
			}
			
			def random = new Random(System.currentTimeMillis())
			
			return response.results[random.nextInt(response.results.size())]
		}
		return null
	}
}
