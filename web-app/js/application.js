if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

$(document).ready(function(){
    $('#slideshow').cycle({
        fx:     'fade',
        speed:  'fast',
        pause:   true,
        timeout: 2000,
        next:   '.next',
        prev:   '.prev'
    });
});

var maxGuesses = 10;
$("#remaining").html( maxGuesses );

$("#startOverBtn").submit(function(event) {
	alert("weee");
    var $form = $( this ),
    url = $form.attr( 'action' );

    $.post( url, {}, function( data ) {} );
    	    
    return false;
});

$("#guessForm").submit(function(event) {

    event.preventDefault(); 
        
    var $form = $( this ),
    	guess = $form.find( 'input[name="guess"]' ).val(),
        url = $form.attr( 'action' );

    if (!/^\d+$/.test(guess)) {
    	return false;
    }
    
    $.post( url, { guess: guess },
      function( data ) {
        var correct = data.correct;
        var higher = data.higher;
        var actualPrice = data.actualPrice;
        var formattedPrice = data.formattedPrice;
        var response;

    	var guessesLeft = $("#remaining").html() - 1;
    	
        if (correct) {
        	response = "Correct! You got it in "+ (maxGuesses - guessesLeft) +" guesses!";
        } else {
        	$("#remaining").html( guessesLeft );
        	
        	if (guessesLeft <= 0) {
        		$("#submitBtn").remove();
	        	response = "You lose! The price was "+ actualPrice;
        	} else {
	        	if (higher) {
		        	response = formattedPrice + "is too LOW!";
		        } else {
		        	response = formattedPrice + "is too HIGH!";
		        }
        	}
        }
        
        $(".latestResponse").each(function(index) {
        	$(this).removeClass('latestResponse')
        });
    	$("#responses").prepend('<li><span class="response latestResponse">'+ response + '</span></li>');
         // var content = $( data ).find( '#content' );
         // .empty().append( content );
      }
    );
    
	return false;
});