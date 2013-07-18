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
          <div class="hero-unit">
            <h1>TeBES</h1>
            <p>Vestibulum viverra consequat fermentum. Quisque a scelerisque massa. Donec eget elit eget massa egestas luctus vel sit amet quam. Sed varius magna sit amet malesuada commodo. Ut ultrices arcu turpis, at hendrerit lorem aliquam quis. Ut nulla lacus, auctor id justo a, suscipit vulputate sapien. In hac habitasse platea dictumst.</p>
            <p><a href="#" class="btn btn-primary btn-large">How To &raquo;</a></p>
          </div>
        </div><!--/span-->
      </div><!--/row-->

      <hr>

    <jsp:include page="footer.jsp" />

    </div><!--/.fluid-container-->
    
    <script type="text/javascript" src="js/bootstrap.min.js"></script>


  </body>
</html>

