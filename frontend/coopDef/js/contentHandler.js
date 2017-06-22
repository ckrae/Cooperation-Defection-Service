/*
 * Loads general content (e.g. header, menu) and provides common functionalities.
 *
 * requires jQuery 2.1.1
 * requires jquery.session.js
 * requires jquery.tablesorter.js
 */


var path = window.location.pathname.split('/');
if(path[path.length - 1] !== "login.html") {
    if($.session.get('isLoggedIn') !== 'true') {
            window.location.replace("login.html");
    }
}

/*
 * Set general page content:
 * Header
 * Menu
 */
$(document).ready(function(){
    /*
     * Menu definition.
     */
    var menuString =
            '\
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">\
                <div class="container">\
                    <!-- Brand and toggle get grouped for better mobile display -->\
                    <div class="navbar-header">\
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">\
                            <span class="sr-only">Toggle navigation</span>\
                            <span class="icon-bar"></span>\
                            <span class="icon-bar"></span>\
                            <span class="icon-bar"></span>\
                        </button>\
                        <a class="navbar-brand" href="simulations.html">C&D Service</a>\
                    </div>\
                    <!-- Collect the nav links, forms, and other content for toggling -->\
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">\
                        <ul class="nav navbar-nav">\
                            <li>\
                                <a href="simulation.html">Simulation</a>\
                            </li>\
                            <li>\
                                <a href="results.html">Evaluation</a>\
                            </li>\
                            <li>\
                                <a href="networks.html">Networks</a>\
                            </li>\
                            <li>\
                                <a href="login.html">Login</a>\
                            </li>\
                        </ul>\
                    </div>\
                    <!-- /.navbar-collapse -->\
                </div>\
                <!-- /.container -->\
            </nav>\
        ';

    $('#navigation').append(menuString);
});

/*
 * Returns an array with the names and values of the
 * query variables of the current url.
 */
function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

/*
 * Returns a url variable of a given name.
 */
function getUrlVar(name) {
    return getUrlVars()[name];
}
