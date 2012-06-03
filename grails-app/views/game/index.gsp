<%@ page import="java.text.NumberFormat" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
    </head>
    <body>
        <div id="content">
			<h2>${heading} <!--  ($${NumberFormat.getInstance().format(post.price)}) --></h2>
			<div id="images">
			    <div id="slideshow">
					<g:each in="${post.images}" var="image">
					   <div>
					       <img src="${image}">
					   </div>
				    </g:each>
			    </div>
			</div>
            <g:form id="guessForm" url="[controller: 'game', action: 'guess']" method="post">
                <p>Guess the price! (<span id="remaining"></span> guesses remaining)</p>
				$<input type="text" name="guess">
	            <input id="submitBtn" type="submit" value="Ask the Gerbil">
            </g:form>
            <ul id="responses"></ul>
            <g:form id="startOverForm" url="[controller: 'game', action: 'startover']" method="post">
                <input id="startOverBtn" type="submit" value="Start Over">
            </g:form>
		</div>
    </body>
</html>