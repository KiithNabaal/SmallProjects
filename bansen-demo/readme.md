#Verizon On-Demand Demo

This is a small demo JavaScript/React.js web application that simulates Verizon on-demand for the web.
I came up with this idea because it seemed like the application would fit well with React.js.

The purpose of this demo is to demonstrate rapid prototyping of a web application while learning a new
JavaScript library (React.js). It took a little over 4 days to design and implement this application.

I developed the application in steps:
	1. First I did a little bit of sketching of how the application should look in MS Paint.
	2. Then, I built the site in HTML and CSS to get the overall look and structure.
	3. I then slowly started adding in React components, using client-side data for state.
	4. Finally, I moved the app over to Node/Express, where I linked the client up to the server using jQuery.

The server-side data comes from a module called 'data' (located in node_modules). If I were to develop this
as a real application, I would use MongoDB instead to retrieve and filter data for the client. I mocked the
data myself using JSON.

As far as the color schemes and font goes, I tried to be as close to Verizon as possible. They pretty much seem
to use red, black, and white. I figured I would stick with this to give a Verizon "look and feel".

How to use the demo:
	1. This is a Node/Express application, so run "node main.js" to get the app started (runs on port 8000).

	2. When the page loads, you will see a red bar on the left side of the screen. Hover your cursor over
	this bar to expose the full option pane.

	3. Once the pane is exposed, you will have several options available to you:
		a. Channels - This has a list of offered channels.
		b. New Releases - This has a list of new movies the cable provider has released.
		c. Saved Items - Previously recorded shows.

	Hovering over each option will pop-up a small panel that will provide more information about that option.
	Clicking on the option text will update the "tv screen", and show a screenshot of that show.

	4. There are also two checkboxes:
		a. Advance the time.
		b. Filter unsubscribed features.

	Advancing the time simulates just that. Different shows are on at different times. Checking this box will
	update channel state to shows currently on. Unchecking this box restores original state.

	I think Verizon allows its users to view unsubscribed channels as a sales tactic, but they do allow you
	to filter these channels out. This checkbox allows you to do just that. Checking this box will disable
	and filter unsubscribed features. This demo assumes the user is not subscribed to new movies or premium
	channels.