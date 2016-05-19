/**
 *   @description JS Class which contains method for REST collaboration.
 *   @author Yauheni Paulavets.
 */
ServiceApi = function() {

    var self = this;

    /**
     *   @description Implement POST request to a server.
     *	@param params POST params
     *	@param url Destination url.
     *	@param done Success callback.
     *	@param fail Fail callback.
     *   @author Yauheni Paulavets
     */
    self._ajaxPost = function( params, url, done, fail ) {

        $.ajax({
            type : "POST",
            timeout : 10000,
            url : url,
            data : params,
            success : done ? done : function( result ) {

                console.log( result );

            },
            error : fail ? fail : function( result ) {

                console.log( result );

            }
        });
    };

    /**
     *   @description Implement GET request to a server.
     *	@param params GET params
     *	@param url Destination url.
     *	@param done Success callback.
     *	@param fail Fail callback.
     *   @author Yauheni Paulavets
     */
    self._ajaxGet = function( params, url, done, fail ) {

        $.ajax({
            type : "GET",
            timeout : 10000,
            url : url,
            data : params,
            success : done ? done : function( result ) {

                console.log( result );

            },
            error : fail ? fail : function( result ) {

                console.log( result );

            }
        });
    };
};

jQuery( document ).ready( function() {

    var serviceApi = new ServiceApi();
    var parameters = {};
    var serviceName = prompt("Input service name, please: ");
    var methodName = prompt("Input method name, please: ");

    var msg = "Input param in \"name:value\" notation or \"No\" otherwise, please: ";
    var param = prompt(msg);
    while(param.toLowerCase() != "no") {
        var nameToValue = param.split(":");
        parameters[nameToValue[0]] = nameToValue[1];
        var param = prompt(msg);
    }

    serviceApi._ajaxPost(
        parameters,
        "/wcsm/" + serviceName + "/" + methodName,
        function( responce ) {

            var data = JSON.stringify(responce, null, '\t');

            if (data) {
                var control = jQuery("<div style='border-bottom: 1px solid silver; border-top: 1px solid silver; width: 100%; height: 100%;'></div>");
                jQuery(".custom-body").append(control);

                if (!ace && window.ace) {
                    ace = window.ace;
                }
                var editor = ace.edit(control[0]);
                editor.getSession().setUseWrapMode(true);

                // theme
                editor.setTheme("ace/theme/chrome");

                // mode
                editor.getSession().setMode("ace/mode/json");

                editor.renderer.setHScrollBarAlwaysVisible(false);
                editor.setShowPrintMargin(false);

                // set data onto editor
                editor.setValue(data);
                editor.clearSelection();

                // clear undo session
                editor.getSession().getUndoManager().reset();
                editor.setReadOnly(true);
            }

        },
        function( result ) {

            alert( 'There are some problems with connection. Try again, please.' );

        }
    );

});