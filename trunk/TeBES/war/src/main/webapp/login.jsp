<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }

      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
    </style>
    <link href="css/bootstrap-responsive.min.css" rel="stylesheet">

     <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

  </head>

  <body>

     <jsp:include page="navbar.jsp" />

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
           <jsp:include page="menu.jsp" />
        </div><!--/span-->
        <div class="span9">
                  <div class="" id="loginModal">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
            <h3>Have an Account?</h3>
          </div>
          <div class="modal-body">
            <div class="well">
              <ul class="nav nav-tabs">
                <li class="active"><a href="#login" data-toggle="tab">Login</a></li>
                <li><a href="#create" data-toggle="tab">Create Account</a></li>
              </ul>
              <div id="myTabContent" class="tab-content">
                <div class="tab-pane active in" id="login">
                  <form class="form-horizontal" action="j_security_check" method="POST">
                    <fieldset>
                      <div id="legend">
                        <legend class="">Login</legend>
                      </div>    
                      <div class="control-group">
                        <!-- Username -->
                        <label class="control-label"  for="username">Username</label>
                        <div class="controls">
                          <input type="text" id="username" name="j_username" placeholder="" class="input-xlarge">
                        </div>
                      </div>
 
                      <div class="control-group">
                        <!-- Password-->
                        <label class="control-label" for="password">Password</label>
                        <div class="controls">
                          <input type="password" id="password" name="j_password" placeholder="" class="input-xlarge">
                        </div>
                      </div>
 
 
                      <div class="control-group">
                        <!-- Button -->
                        <div class="controls">
                          <button type="submit" class="btn btn-success">Login</button>
                        </div>
                      </div>
                    </fieldset>
                  </form>                
                </div>
                <div class="tab-pane fade" id="create">
                  <form id="tab">
                    <label>Username</label>
                    <input type="text" value="" class="input-xlarge">
                    <label>First Name</label>
                    <input type="text" value="" class="input-xlarge">
                    <label>Last Name</label>
                    <input type="text" value="" class="input-xlarge">
                    <label>Email</label>
                    <input type="text" value="" class="input-xlarge">
                    <label>Address</label>
                    <textarea value="Smith" rows="3" class="input-xlarge">
                    </textarea>
 
                    <div>
                      <button class="btn btn-primary">Create Account</button>
                    </div>
                  </form>
                </div>
            </div>
          </div>
        </div>
      </div><!--/row-->

      <hr>

    <jsp:include page="footer.jsp" />

    </div><!--/.fluid-container-->
    
    <script type="text/javascript" src="js/bootstrap.min.js"></script>


  </body>
</html>