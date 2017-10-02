


<!DOCTYPE html>
<html lang="en" class="">
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# object: http://ogp.me/ns/object# article: http://ogp.me/ns/article# profile: http://ogp.me/ns/profile#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Language" content="en">
    
    
    <title>Proj4Leaflet/proj4-compressed.js at master · kartena/Proj4Leaflet · GitHub</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub">
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub">
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-touch-icon-114.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-touch-icon-114.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-touch-icon-144.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-touch-icon-144.png">
    <meta property="fb:app_id" content="1401488693436528">

      <meta content="@github" name="twitter:site" /><meta content="summary" name="twitter:card" /><meta content="kartena/Proj4Leaflet" name="twitter:title" /><meta content="Proj4Leaflet - Smooth Proj4js integration with Leaflet." name="twitter:description" /><meta content="https://avatars3.githubusercontent.com/u/1246578?v=3&amp;s=400" name="twitter:image:src" />
      <meta content="GitHub" property="og:site_name" /><meta content="object" property="og:type" /><meta content="https://avatars3.githubusercontent.com/u/1246578?v=3&amp;s=400" property="og:image" /><meta content="kartena/Proj4Leaflet" property="og:title" /><meta content="https://github.com/kartena/Proj4Leaflet" property="og:url" /><meta content="Proj4Leaflet - Smooth Proj4js integration with Leaflet." property="og:description" />
      <meta name="browser-stats-url" content="https://api.github.com/_private/browser/stats">
    <meta name="browser-errors-url" content="https://api.github.com/_private/browser/errors">
    <link rel="assets" href="https://assets-cdn.github.com/">
    
    <meta name="pjax-timeout" content="1000">
    

    <meta name="msapplication-TileImage" content="/windows-tile.png">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="selected-link" value="repo_source" data-pjax-transient>
      <meta name="google-analytics" content="UA-3769691-2">

    <meta content="collector.githubapp.com" name="octolytics-host" /><meta content="collector-cdn.github.com" name="octolytics-script-host" /><meta content="github" name="octolytics-app-id" /><meta content="BA03BFBE:1F50:1A2D8A5:55818AA9" name="octolytics-dimension-request_id" />
    
    <meta content="Rails, view, blob#show" name="analytics-event" />
    <meta class="js-ga-set" name="dimension1" content="Logged Out">
    <meta name="is-dotcom" content="true">
      <meta name="hostname" content="github.com">
    <meta name="user-login" content="">

      <link rel="icon" sizes="any" mask href="https://assets-cdn.github.com/pinned-octocat.svg">
      <meta name="theme-color" content="#4078c0">
      <link rel="icon" type="image/x-icon" href="https://assets-cdn.github.com/favicon.ico">


    <meta content="authenticity_token" name="csrf-param" />
<meta content="AC/pMt9Q3yQtY6RgqBzQAtKfgkafuMmIPj+zBqPj7G73RMVQ/WN2J+Ac55689+WCJK+HgGVTmWCRyNt+GGsxpA==" name="csrf-token" />

    <link crossorigin="anonymous" href="https://assets-cdn.github.com/assets/github/index-4a8149b8016d5359ad2c49df2fad7583fdb965107b7f7de91a17b687336fef9f.css" media="all" rel="stylesheet" />
    <link crossorigin="anonymous" href="https://assets-cdn.github.com/assets/github2/index-c97c34f354d881281585ec0a7f62b8a8c7c53c18bf0d31f189803de93868c450.css" media="all" rel="stylesheet" />
    
    


    <meta http-equiv="x-pjax-version" content="3de249314b795cd27e4b9992a06e2862">

      
  <meta name="description" content="Proj4Leaflet - Smooth Proj4js integration with Leaflet.">
  <meta name="go-import" content="github.com/kartena/Proj4Leaflet git https://github.com/kartena/Proj4Leaflet.git">

  <meta content="1246578" name="octolytics-dimension-user_id" /><meta content="kartena" name="octolytics-dimension-user_login" /><meta content="2933212" name="octolytics-dimension-repository_id" /><meta content="kartena/Proj4Leaflet" name="octolytics-dimension-repository_nwo" /><meta content="true" name="octolytics-dimension-repository_public" /><meta content="false" name="octolytics-dimension-repository_is_fork" /><meta content="2933212" name="octolytics-dimension-repository_network_root_id" /><meta content="kartena/Proj4Leaflet" name="octolytics-dimension-repository_network_root_nwo" />
  <link href="https://github.com/kartena/Proj4Leaflet/commits/master.atom" rel="alternate" title="Recent Commits to Proj4Leaflet:master" type="application/atom+xml">

  </head>


  <body class="logged_out  env-production linux vis-public page-blob">
    <a href="#start-of-content" tabindex="1" class="accessibility-aid js-skip-to-content">Skip to content</a>
    <div class="wrapper">
      
      
      


        
        <div class="header header-logged-out" role="banner">
  <div class="container clearfix">

    <a class="header-logo-wordmark" href="https://github.com/" data-ga-click="(Logged out) Header, go to homepage, icon:logo-wordmark">
      <span class="mega-octicon octicon-logo-github"></span>
    </a>

    <div class="header-actions" role="navigation">
        <a class="btn btn-primary" href="/join" data-ga-click="(Logged out) Header, clicked Sign up, text:sign-up">Sign up</a>
      <a class="btn" href="/login?return_to=%2Fkartena%2FProj4Leaflet%2Fblob%2Fmaster%2Flib%2Fproj4-compressed.js" data-ga-click="(Logged out) Header, clicked Sign in, text:sign-in">Sign in</a>
    </div>

    <div class="site-search repo-scope js-site-search" role="search">
      <form accept-charset="UTF-8" action="/kartena/Proj4Leaflet/search" class="js-site-search-form" data-global-search-url="/search" data-repo-search-url="/kartena/Proj4Leaflet/search" method="get"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /></div>
  <label class="js-chromeless-input-container form-control">
    <div class="scope-badge">This repository</div>
    <input type="text"
      class="js-site-search-focus js-site-search-field is-clearable chromeless-input"
      data-hotkey="s"
      name="q"
      placeholder="Search"
      data-global-scope-placeholder="Search GitHub"
      data-repo-scope-placeholder="Search"
      tabindex="1"
      autocapitalize="off">
  </label>
</form>
    </div>

      <ul class="header-nav left" role="navigation">
          <li class="header-nav-item">
            <a class="header-nav-link" href="/explore" data-ga-click="(Logged out) Header, go to explore, text:explore">Explore</a>
          </li>
          <li class="header-nav-item">
            <a class="header-nav-link" href="/features" data-ga-click="(Logged out) Header, go to features, text:features">Features</a>
          </li>
          <li class="header-nav-item">
            <a class="header-nav-link" href="https://enterprise.github.com/" data-ga-click="(Logged out) Header, go to enterprise, text:enterprise">Enterprise</a>
          </li>
          <li class="header-nav-item">
            <a class="header-nav-link" href="/blog" data-ga-click="(Logged out) Header, go to blog, text:blog">Blog</a>
          </li>
      </ul>

  </div>
</div>



      <div id="start-of-content" class="accessibility-aid"></div>
          <div class="site" itemscope itemtype="http://schema.org/WebPage">
    <div id="js-flash-container">
      
    </div>
    <div class="pagehead repohead instapaper_ignore readability-menu">
      <div class="container">

        
<ul class="pagehead-actions">

  <li>
      <a href="/login?return_to=%2Fkartena%2FProj4Leaflet"
    class="btn btn-sm btn-with-count tooltipped tooltipped-n"
    aria-label="You must be signed in to watch a repository" rel="nofollow">
    <span class="octicon octicon-eye"></span>
    Watch
  </a>
  <a class="social-count" href="/kartena/Proj4Leaflet/watchers">
    28
  </a>

  </li>

  <li>
      <a href="/login?return_to=%2Fkartena%2FProj4Leaflet"
    class="btn btn-sm btn-with-count tooltipped tooltipped-n"
    aria-label="You must be signed in to star a repository" rel="nofollow">
    <span class="octicon octicon-star"></span>
    Star
  </a>

    <a class="social-count js-social-count" href="/kartena/Proj4Leaflet/stargazers">
      166
    </a>

  </li>

    <li>
      <a href="/login?return_to=%2Fkartena%2FProj4Leaflet"
        class="btn btn-sm btn-with-count tooltipped tooltipped-n"
        aria-label="You must be signed in to fork a repository" rel="nofollow">
        <span class="octicon octicon-repo-forked"></span>
        Fork
      </a>
      <a href="/kartena/Proj4Leaflet/network" class="social-count">
        57
      </a>
    </li>
</ul>

        <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title public">
          <span class="mega-octicon octicon-repo"></span>
          <span class="author"><a href="/kartena" class="url fn" itemprop="url" rel="author"><span itemprop="title">kartena</span></a></span><!--
       --><span class="path-divider">/</span><!--
       --><strong><a href="/kartena/Proj4Leaflet" data-pjax="#js-repo-pjax-container">Proj4Leaflet</a></strong>

          <span class="page-context-loader">
            <img alt="" height="16" src="https://assets-cdn.github.com/images/spinners/octocat-spinner-32.gif" width="16" />
          </span>

        </h1>
      </div><!-- /.container -->
    </div><!-- /.repohead -->

    <div class="container">
      <div class="repository-with-sidebar repo-container new-discussion-timeline  ">
        <div class="repository-sidebar clearfix">
            
<nav class="sunken-menu repo-nav js-repo-nav js-sidenav-container-pjax js-octicon-loaders"
     role="navigation"
     data-pjax="#js-repo-pjax-container"
     data-issue-count-url="/kartena/Proj4Leaflet/issues/counts">
  <ul class="sunken-menu-group">
    <li class="tooltipped tooltipped-w" aria-label="Code">
      <a href="/kartena/Proj4Leaflet" aria-label="Code" class="selected js-selected-navigation-item sunken-menu-item" data-hotkey="g c" data-selected-links="repo_source repo_downloads repo_commits repo_releases repo_tags repo_branches /kartena/Proj4Leaflet">
        <span class="octicon octicon-code"></span> <span class="full-word">Code</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/images/spinners/octocat-spinner-32.gif" width="16" />
</a>    </li>

      <li class="tooltipped tooltipped-w" aria-label="Issues">
        <a href="/kartena/Proj4Leaflet/issues" aria-label="Issues" class="js-selected-navigation-item sunken-menu-item" data-hotkey="g i" data-selected-links="repo_issues repo_labels repo_milestones /kartena/Proj4Leaflet/issues">
          <span class="octicon octicon-issue-opened"></span> <span class="full-word">Issues</span>
          <span class="js-issue-replace-counter"></span>
          <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>

    <li class="tooltipped tooltipped-w" aria-label="Pull requests">
      <a href="/kartena/Proj4Leaflet/pulls" aria-label="Pull requests" class="js-selected-navigation-item sunken-menu-item" data-hotkey="g p" data-selected-links="repo_pulls /kartena/Proj4Leaflet/pulls">
          <span class="octicon octicon-git-pull-request"></span> <span class="full-word">Pull requests</span>
          <span class="js-pull-replace-counter"></span>
          <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/images/spinners/octocat-spinner-32.gif" width="16" />
</a>    </li>

  </ul>
  <div class="sunken-menu-separator"></div>
  <ul class="sunken-menu-group">

    <li class="tooltipped tooltipped-w" aria-label="Pulse">
      <a href="/kartena/Proj4Leaflet/pulse" aria-label="Pulse" class="js-selected-navigation-item sunken-menu-item" data-selected-links="pulse /kartena/Proj4Leaflet/pulse">
        <span class="octicon octicon-pulse"></span> <span class="full-word">Pulse</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/images/spinners/octocat-spinner-32.gif" width="16" />
</a>    </li>

    <li class="tooltipped tooltipped-w" aria-label="Graphs">
      <a href="/kartena/Proj4Leaflet/graphs" aria-label="Graphs" class="js-selected-navigation-item sunken-menu-item" data-selected-links="repo_graphs repo_contributors /kartena/Proj4Leaflet/graphs">
        <span class="octicon octicon-graph"></span> <span class="full-word">Graphs</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/images/spinners/octocat-spinner-32.gif" width="16" />
</a>    </li>
  </ul>


</nav>

              <div class="only-with-full-nav">
                  
<div class="js-clone-url clone-url open"
  data-protocol-type="http">
  <h3><span class="text-emphasized">HTTPS</span> clone URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="https://github.com/kartena/Proj4Leaflet.git" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard btn btn-sm zeroclipboard-button tooltipped tooltipped-s" data-copied-hint="Copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>

  
<div class="js-clone-url clone-url "
  data-protocol-type="subversion">
  <h3><span class="text-emphasized">Subversion</span> checkout URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="https://github.com/kartena/Proj4Leaflet" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard btn btn-sm zeroclipboard-button tooltipped tooltipped-s" data-copied-hint="Copied!" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>



<div class="clone-options">You can clone with
  <form accept-charset="UTF-8" action="/users/set_protocol?protocol_selector=http&amp;protocol_type=clone" class="inline-form js-clone-selector-form " data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="6anBZGCONP1Jle7lG7jod9OKrWBv2+ukhguUmOtUPJM+wnJx1WBwMZThlJcnW/+IDpeO/1GiqRihWJRpRqrLng==" /></div><button class="btn-link js-clone-selector" data-protocol="http" type="submit">HTTPS</button></form> or <form accept-charset="UTF-8" action="/users/set_protocol?protocol_selector=subversion&amp;protocol_type=clone" class="inline-form js-clone-selector-form " data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="72kHlz53C9KFbNeK3X42Rwxg87ac8J/+cseOsVlOiqYHLcu/ESzUCbOpL3+PheM8HXxatawEw8MsVQeKRmdfOg==" /></div><button class="btn-link js-clone-selector" data-protocol="subversion" type="submit">Subversion</button></form>.
  <a href="https://help.github.com/articles/which-remote-url-should-i-use" class="help tooltipped tooltipped-n" aria-label="Get help on which URL is right for you.">
    <span class="octicon octicon-question"></span>
  </a>
</div>




                <a href="/kartena/Proj4Leaflet/archive/master.zip"
                   class="btn btn-sm sidebar-button"
                   aria-label="Download the contents of kartena/Proj4Leaflet as a zip file"
                   title="Download the contents of kartena/Proj4Leaflet as a zip file"
                   rel="nofollow">
                  <span class="octicon octicon-cloud-download"></span>
                  Download ZIP
                </a>
              </div>
        </div><!-- /.repository-sidebar -->

        <div id="js-repo-pjax-container" class="repository-content context-loader-container" data-pjax-container>

          

<a href="/kartena/Proj4Leaflet/blob/2c5d717789cc804da2b64702db563dcf124d3e4c/lib/proj4-compressed.js" class="hidden js-permalink-shortcut" data-hotkey="y">Permalink</a>

<!-- blob contrib key: blob_contributors:v21:6ffee74d23a6eb890ab14c7997a05e6b -->

<div class="file-navigation js-zeroclipboard-container">
  
<div class="select-menu js-menu-container js-select-menu left">
  <span class="btn btn-sm select-menu-button js-menu-target css-truncate" data-hotkey="w"
    data-ref="master"
    title="master"
    role="button" aria-label="Switch branches or tags" tabindex="0" aria-haspopup="true">
    <span class="octicon octicon-git-branch"></span>
    <i>branch:</i>
    <span class="js-select-button css-truncate-target">master</span>
  </span>

  <div class="select-menu-modal-holder js-menu-content js-navigation-container" data-pjax aria-hidden="true">

    <div class="select-menu-modal">
      <div class="select-menu-header">
        <span class="select-menu-title">Switch branches/tags</span>
        <span class="octicon octicon-x js-menu-close" role="button" aria-label="Close"></span>
      </div>

      <div class="select-menu-filters">
        <div class="select-menu-text-filter">
          <input type="text" aria-label="Filter branches/tags" id="context-commitish-filter-field" class="js-filterable-field js-navigation-enable" placeholder="Filter branches/tags">
        </div>
        <div class="select-menu-tabs">
          <ul>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="branches" data-filter-placeholder="Filter branches/tags" class="js-select-menu-tab">Branches</a>
            </li>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="tags" data-filter-placeholder="Find a tag…" class="js-select-menu-tab">Tags</a>
            </li>
          </ul>
        </div>
      </div>

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="branches">

        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/kartena/Proj4Leaflet/blob/0.4/lib/proj4-compressed.js"
               data-name="0.4"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="0.4">
                0.4
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/kartena/Proj4Leaflet/blob/0.5/lib/proj4-compressed.js"
               data-name="0.5"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="0.5">
                0.5
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/kartena/Proj4Leaflet/blob/0.6/lib/proj4-compressed.js"
               data-name="0.6"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="0.6">
                0.6
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/kartena/Proj4Leaflet/blob/leaflet-proj-refactor/lib/proj4-compressed.js"
               data-name="leaflet-proj-refactor"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="leaflet-proj-refactor">
                leaflet-proj-refactor
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open selected"
               href="/kartena/Proj4Leaflet/blob/master/lib/proj4-compressed.js"
               data-name="master"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="master">
                master
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/kartena/Proj4Leaflet/blob/tms-layer/lib/proj4-compressed.js"
               data-name="tms-layer"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="tms-layer">
                tms-layer
              </span>
            </a>
        </div>

          <div class="select-menu-no-results">Nothing to show</div>
      </div>

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="tags">
        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/kartena/Proj4Leaflet/tree/v0.4.5/lib/proj4-compressed.js"
                 data-name="v0.4.5"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="v0.4.5">v0.4.5</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/kartena/Proj4Leaflet/tree/0.7.1/lib/proj4-compressed.js"
                 data-name="0.7.1"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="0.7.1">0.7.1</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/kartena/Proj4Leaflet/tree/0.7.0/lib/proj4-compressed.js"
                 data-name="0.7.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="0.7.0">0.7.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/kartena/Proj4Leaflet/tree/0.6.2/lib/proj4-compressed.js"
                 data-name="0.6.2"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="0.6.2">0.6.2</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/kartena/Proj4Leaflet/tree/0.6.1/lib/proj4-compressed.js"
                 data-name="0.6.1"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="0.6.1">0.6.1</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/kartena/Proj4Leaflet/tree/0.6.0/lib/proj4-compressed.js"
                 data-name="0.6.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="0.6.0">0.6.0</a>
            </div>
        </div>

        <div class="select-menu-no-results">Nothing to show</div>
      </div>

    </div>
  </div>
</div>

  <div class="btn-group right">
    <a href="/kartena/Proj4Leaflet/find/master"
          class="js-show-file-finder btn btn-sm empty-icon tooltipped tooltipped-s"
          data-pjax
          data-hotkey="t"
          aria-label="Quickly jump between files">
      <span class="octicon octicon-list-unordered"></span>
    </a>
    <button aria-label="Copy file path to clipboard" class="js-zeroclipboard btn btn-sm zeroclipboard-button tooltipped tooltipped-s" data-copied-hint="Copied!" type="button"><span class="octicon octicon-clippy"></span></button>
  </div>

  <div class="breadcrumb js-zeroclipboard-target">
    <span class="repo-root js-repo-root"><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/kartena/Proj4Leaflet" class="" data-branch="master" data-pjax="true" itemscope="url"><span itemprop="title">Proj4Leaflet</span></a></span></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/kartena/Proj4Leaflet/tree/master/lib" class="" data-branch="master" data-pjax="true" itemscope="url"><span itemprop="title">lib</span></a></span><span class="separator">/</span><strong class="final-path">proj4-compressed.js</strong>
  </div>
</div>


  <div class="commit file-history-tease">
    <div class="file-history-tease-header">
        <img alt="@sheppard" class="avatar" height="24" src="https://avatars0.githubusercontent.com/u/1863675?v=3&amp;s=48" width="24" />
        <span class="author"><a href="/sheppard" rel="contributor">sheppard</a></span>
        <time datetime="2013-09-11T18:53:40Z" is="relative-time">Sep 11, 2013</time>
        <div class="commit-title">
            <a href="/kartena/Proj4Leaflet/commit/208ca1869dda7e24a6a4d5b4735914e7b4f21e05" class="message" data-pjax="true" title="update to proj4js v1.3.4

- dist/proj4.js -&gt; proj4-compressed.js
- dist/amd/proj4.js -&gt; proj4.js">update to proj4js v1.3.4</a>
        </div>
    </div>

    <div class="participation">
      <p class="quickstat">
        <a href="#blob_contributors_box" rel="facebox">
          <strong>1</strong>
           contributor
        </a>
      </p>
      
    </div>
    <div id="blob_contributors_box" style="display:none">
      <h2 class="facebox-header">Users who have contributed to this file</h2>
      <ul class="facebox-user-list">
          <li class="facebox-user-list-item">
            <img alt="@sheppard" height="24" src="https://avatars0.githubusercontent.com/u/1863675?v=3&amp;s=48" width="24" />
            <a href="/sheppard">sheppard</a>
          </li>
      </ul>
    </div>
  </div>

<div class="file">
  <div class="file-header">
    <div class="file-actions">

      <div class="btn-group">
        <a href="/kartena/Proj4Leaflet/raw/master/lib/proj4-compressed.js" class="btn btn-sm " id="raw-url">Raw</a>
          <a href="/kartena/Proj4Leaflet/blame/master/lib/proj4-compressed.js" class="btn btn-sm js-update-url-with-hash">Blame</a>
        <a href="/kartena/Proj4Leaflet/commits/master/lib/proj4-compressed.js" class="btn btn-sm " rel="nofollow">History</a>
      </div>


          <button type="button" class="octicon-btn disabled tooltipped tooltipped-n" aria-label="You must be signed in to make or propose changes">
            <span class="octicon octicon-pencil"></span>
          </button>

        <button type="button" class="octicon-btn octicon-btn-danger disabled tooltipped tooltipped-n" aria-label="You must be signed in to make or propose changes">
          <span class="octicon octicon-trashcan"></span>
        </button>
    </div>

    <div class="file-info">
        3 lines (3 sloc)
        <span class="file-info-divider"></span>
      70.607 kB
    </div>
  </div>
  
  <div class="blob-wrapper data type-javascript">
      <table class="highlight tab-size js-file-line-container" data-tab-size="8">
      <tr>
        <td id="L1" class="blob-num js-line-number" data-line-number="1"></td>
        <td id="LC1" class="blob-code blob-code-inner js-file-line">!function(t,s){&quot;function&quot;==typeof define&amp;&amp;define.amd?define(s):&quot;undefined&quot;!=typeof module?module.exports=s():t.proj4=s()}(this,function(){var t,s,i;return function(a){function h(t,s){return x.call(t,s)}function e(t,s){var i,a,h,e,n,r,o,l,u,c,M=s&amp;&amp;s.split(&quot;/&quot;),f=y.map,m=f&amp;&amp;f[&quot;*&quot;]||{};if(t&amp;&amp;&quot;.&quot;===t.charAt(0))if(s){for(M=M.slice(0,M.length-1),t=M.concat(t.split(&quot;/&quot;)),l=0;l&lt;t.length;l+=1)if(c=t[l],&quot;.&quot;===c)t.splice(l,1),l-=1;else if(&quot;..&quot;===c){if(1===l&amp;&amp;(&quot;..&quot;===t[2]||&quot;..&quot;===t[0]))break;l&gt;0&amp;&amp;(t.splice(l-1,2),l-=2)}t=t.join(&quot;/&quot;)}else 0===t.indexOf(&quot;./&quot;)&amp;&amp;(t=t.substring(2));if((M||m)&amp;&amp;f){for(i=t.split(&quot;/&quot;),l=i.length;l&gt;0;l-=1){if(a=i.slice(0,l).join(&quot;/&quot;),M)for(u=M.length;u&gt;0;u-=1)if(h=f[M.slice(0,u).join(&quot;/&quot;)],h&amp;&amp;(h=h[a])){e=h,n=l;break}if(e)break;!r&amp;&amp;m&amp;&amp;m[a]&amp;&amp;(r=m[a],o=l)}!e&amp;&amp;r&amp;&amp;(e=r,n=o),e&amp;&amp;(i.splice(0,n,e),t=i.join(&quot;/&quot;))}return t}function n(t,s){return function(){return f.apply(a,v.call(arguments,0).concat([t,s]))}}function r(t){return function(s){return e(s,t)}}function o(t){return function(s){_[t]=s}}function l(t){if(h(d,t)){var s=d[t];delete d[t],g[t]=!0,M.apply(a,s)}if(!h(_,t)&amp;&amp;!h(g,t))throw new Error(&quot;No &quot;+t);return _[t]}function u(t){var s,i=t?t.indexOf(&quot;!&quot;):-1;return i&gt;-1&amp;&amp;(s=t.substring(0,i),t=t.substring(i+1,t.length)),[s,t]}function c(t){return function(){return y&amp;&amp;y.config&amp;&amp;y.config[t]||{}}}var M,f,m,p,_={},d={},y={},g={},x=Object.prototype.hasOwnProperty,v=[].slice;m=function(t,s){var i,a=u(t),h=a[0];return t=a[1],h&amp;&amp;(h=e(h,s),i=l(h)),h?t=i&amp;&amp;i.normalize?i.normalize(t,r(s)):e(t,s):(t=e(t,s),a=u(t),h=a[0],t=a[1],h&amp;&amp;(i=l(h))),{f:h?h+&quot;!&quot;+t:t,n:t,pr:h,p:i}},p={require:function(t){return n(t)},exports:function(t){var s=_[t];return&quot;undefined&quot;!=typeof s?s:_[t]={}},module:function(t){return{id:t,uri:&quot;&quot;,exports:_[t],config:c(t)}}},M=function(t,s,i,e){var r,u,c,M,f,y,x=[];if(e=e||t,&quot;function&quot;==typeof i){for(s=!s.length&amp;&amp;i.length?[&quot;require&quot;,&quot;exports&quot;,&quot;module&quot;]:s,f=0;f&lt;s.length;f+=1)if(M=m(s[f],e),u=M.f,&quot;require&quot;===u)x[f]=p.require(t);else if(&quot;exports&quot;===u)x[f]=p.exports(t),y=!0;else if(&quot;module&quot;===u)r=x[f]=p.module(t);else if(h(_,u)||h(d,u)||h(g,u))x[f]=l(u);else{if(!M.p)throw new Error(t+&quot; missing &quot;+u);M.p.load(M.n,n(e,!0),o(u),{}),x[f]=_[u]}c=i.apply(_[t],x),t&amp;&amp;(r&amp;&amp;r.exports!==a&amp;&amp;r.exports!==_[t]?_[t]=r.exports:c===a&amp;&amp;y||(_[t]=c))}else t&amp;&amp;(_[t]=i)},t=s=f=function(t,s,i,h,e){return&quot;string&quot;==typeof t?p[t]?p[t](s):l(m(t,s).f):(t.splice||(y=t,s.splice?(t=s,s=i,i=null):t=a),s=s||function(){},&quot;function&quot;==typeof i&amp;&amp;(i=h,h=e),h?M(a,t,s,i):setTimeout(function(){M(a,t,s,i)},4),f)},f.config=function(t){return y=t,y.deps&amp;&amp;f(y.deps,y.callback),f},t._defined=_,i=function(t,s,i){s.splice||(i=s,s=[]),h(_,t)||h(d,t)||(d[t]=[t,s,i])},i.amd={jQuery:!0}}(),i(&quot;node_modules/almond/almond&quot;,function(){}),i(&quot;proj4/mgrs&quot;,[&quot;require&quot;,&quot;exports&quot;,&quot;module&quot;],function(t,s){function i(t){return t*(Math.PI/180)}function a(t){return 180*(t/Math.PI)}function h(t){var s,a,h,e,r,o,l,u,c,M=t.lat,f=t.lon,m=6378137,p=.00669438,_=.9996,d=i(M),y=i(f);c=Math.floor((f+180)/6)+1,180===f&amp;&amp;(c=60),M&gt;=56&amp;&amp;64&gt;M&amp;&amp;f&gt;=3&amp;&amp;12&gt;f&amp;&amp;(c=32),M&gt;=72&amp;&amp;84&gt;M&amp;&amp;(f&gt;=0&amp;&amp;9&gt;f?c=31:f&gt;=9&amp;&amp;21&gt;f?c=33:f&gt;=21&amp;&amp;33&gt;f?c=35:f&gt;=33&amp;&amp;42&gt;f&amp;&amp;(c=37)),s=6*(c-1)-180+3,u=i(s),a=p/(1-p),h=m/Math.sqrt(1-p*Math.sin(d)*Math.sin(d)),e=Math.tan(d)*Math.tan(d),r=a*Math.cos(d)*Math.cos(d),o=Math.cos(d)*(y-u),l=m*((1-p/4-3*p*p/64-5*p*p*p/256)*d-(3*p/8+3*p*p/32+45*p*p*p/1024)*Math.sin(2*d)+(15*p*p/256+45*p*p*p/1024)*Math.sin(4*d)-35*p*p*p/3072*Math.sin(6*d));var g=_*h*(o+(1-e+r)*o*o*o/6+(5-18*e+e*e+72*r-58*a)*o*o*o*o*o/120)+5e5,x=_*(l+h*Math.tan(d)*(o*o/2+(5-e+9*r+4*r*r)*o*o*o*o/24+(61-58*e+e*e+600*r-330*a)*o*o*o*o*o*o/720));return 0&gt;M&amp;&amp;(x+=1e7),{northing:Math.round(x),easting:Math.round(g),zoneNumber:c,zoneLetter:n(M)}}function e(t){var s=t.northing,i=t.easting,h=t.zoneLetter,n=t.zoneNumber;if(0&gt;n||n&gt;60)return null;var r,o,l,u,c,M,f,m,p,_,d=.9996,y=6378137,g=.00669438,x=(1-Math.sqrt(1-g))/(1+Math.sqrt(1-g)),v=i-5e5,P=s;&quot;N&quot;&gt;h&amp;&amp;(P-=1e7),m=6*(n-1)-180+3,r=g/(1-g),f=P/d,p=f/(y*(1-g/4-3*g*g/64-5*g*g*g/256)),_=p+(3*x/2-27*x*x*x/32)*Math.sin(2*p)+(21*x*x/16-55*x*x*x*x/32)*Math.sin(4*p)+151*x*x*x/96*Math.sin(6*p),o=y/Math.sqrt(1-g*Math.sin(_)*Math.sin(_)),l=Math.tan(_)*Math.tan(_),u=r*Math.cos(_)*Math.cos(_),c=y*(1-g)/Math.pow(1-g*Math.sin(_)*Math.sin(_),1.5),M=v/(o*d);var b=_-o*Math.tan(_)/c*(M*M/2-(5+3*l+10*u-4*u*u-9*r)*M*M*M*M/24+(61+90*l+298*u+45*l*l-252*r-3*u*u)*M*M*M*M*M*M/720);b=a(b);var C=(M-(1+2*l+u)*M*M*M/6+(5-2*u+28*l-3*u*u+8*r+24*l*l)*M*M*M*M*M/120)/Math.cos(_);C=m+a(C);var S;if(t.accuracy){var j=e({northing:t.northing+t.accuracy,easting:t.easting+t.accuracy,zoneLetter:t.zoneLetter,zoneNumber:t.zoneNumber});S={top:j.lat,right:j.lon,bottom:b,left:C}}else S={lat:b,lon:C};return S}function n(t){var s=&quot;Z&quot;;return 84&gt;=t&amp;&amp;t&gt;=72?s=&quot;X&quot;:72&gt;t&amp;&amp;t&gt;=64?s=&quot;W&quot;:64&gt;t&amp;&amp;t&gt;=56?s=&quot;V&quot;:56&gt;t&amp;&amp;t&gt;=48?s=&quot;U&quot;:48&gt;t&amp;&amp;t&gt;=40?s=&quot;T&quot;:40&gt;t&amp;&amp;t&gt;=32?s=&quot;S&quot;:32&gt;t&amp;&amp;t&gt;=24?s=&quot;R&quot;:24&gt;t&amp;&amp;t&gt;=16?s=&quot;Q&quot;:16&gt;t&amp;&amp;t&gt;=8?s=&quot;P&quot;:8&gt;t&amp;&amp;t&gt;=0?s=&quot;N&quot;:0&gt;t&amp;&amp;t&gt;=-8?s=&quot;M&quot;:-8&gt;t&amp;&amp;t&gt;=-16?s=&quot;L&quot;:-16&gt;t&amp;&amp;t&gt;=-24?s=&quot;K&quot;:-24&gt;t&amp;&amp;t&gt;=-32?s=&quot;J&quot;:-32&gt;t&amp;&amp;t&gt;=-40?s=&quot;H&quot;:-40&gt;t&amp;&amp;t&gt;=-48?s=&quot;G&quot;:-48&gt;t&amp;&amp;t&gt;=-56?s=&quot;F&quot;:-56&gt;t&amp;&amp;t&gt;=-64?s=&quot;E&quot;:-64&gt;t&amp;&amp;t&gt;=-72?s=&quot;D&quot;:-72&gt;t&amp;&amp;t&gt;=-80&amp;&amp;(s=&quot;C&quot;),s}function r(t,s){var i=&quot;&quot;+t.easting,a=&quot;&quot;+t.northing;return t.zoneNumber+t.zoneLetter+o(t.easting,t.northing,t.zoneNumber)+i.substr(i.length-5,s)+a.substr(a.length-5,s)}function o(t,s,i){var a=l(i),h=Math.floor(t/1e5),e=Math.floor(s/1e5)%20;return u(h,e,a)}function l(t){var s=t%p;return 0===s&amp;&amp;(s=p),s}function u(t,s,i){var a=i-1,h=_.charCodeAt(a),e=d.charCodeAt(a),n=h+t-1,r=e+s,o=!1;n&gt;P&amp;&amp;(n=n-P+y-1,o=!0),(n===g||g&gt;h&amp;&amp;n&gt;g||(n&gt;g||g&gt;h)&amp;&amp;o)&amp;&amp;n++,(n===x||x&gt;h&amp;&amp;n&gt;x||(n&gt;x||x&gt;h)&amp;&amp;o)&amp;&amp;(n++,n===g&amp;&amp;n++),n&gt;P&amp;&amp;(n=n-P+y-1),r&gt;v?(r=r-v+y-1,o=!0):o=!1,(r===g||g&gt;e&amp;&amp;r&gt;g||(r&gt;g||g&gt;e)&amp;&amp;o)&amp;&amp;r++,(r===x||x&gt;e&amp;&amp;r&gt;x||(r&gt;x||x&gt;e)&amp;&amp;o)&amp;&amp;(r++,r===g&amp;&amp;r++),r&gt;v&amp;&amp;(r=r-v+y-1);var l=String.fromCharCode(n)+String.fromCharCode(r);return l}function c(t){if(t&amp;&amp;0===t.length)throw&quot;MGRSPoint coverting from nothing&quot;;for(var s,i=t.length,a=null,h=&quot;&quot;,e=0;!/[A-Z]/.test(s=t.charAt(e));){if(e&gt;=2)throw&quot;MGRSPoint bad conversion from: &quot;+t;h+=s,e++}var n=parseInt(h,10);if(0===e||e+3&gt;i)throw&quot;MGRSPoint bad conversion from: &quot;+t;var r=t.charAt(e++);if(&quot;A&quot;&gt;=r||&quot;B&quot;===r||&quot;Y&quot;===r||r&gt;=&quot;Z&quot;||&quot;I&quot;===r||&quot;O&quot;===r)throw&quot;MGRSPoint zone letter &quot;+r+&quot; not handled: &quot;+t;a=t.substring(e,e+=2);for(var o=l(n),u=M(a.charAt(0),o),c=f(a.charAt(1),o);c&lt;m(r);)c+=2e6;var p=i-e;if(0!==p%2)throw&quot;MGRSPoint has to have an even number \nof digits after the zone letter and two 100km letters - front \nhalf for easting meters, second half for \nnorthing meters&quot;+t;var _,d,y,g,x,v=p/2,P=0,b=0;return v&gt;0&amp;&amp;(_=1e5/Math.pow(10,v),d=t.substring(e,e+v),P=parseFloat(d)*_,y=t.substring(e+v),b=parseFloat(y)*_),g=P+u,x=b+c,{easting:g,northing:x,zoneLetter:r,zoneNumber:n,accuracy:_}}function M(t,s){for(var i=_.charCodeAt(s-1),a=1e5,h=!1;i!==t.charCodeAt(0);){if(i++,i===g&amp;&amp;i++,i===x&amp;&amp;i++,i&gt;P){if(h)throw&quot;Bad character: &quot;+t;i=y,h=!0}a+=1e5}return a}function f(t,s){if(t&gt;&quot;V&quot;)throw&quot;MGRSPoint given invalid Northing &quot;+t;for(var i=d.charCodeAt(s-1),a=0,h=!1;i!==t.charCodeAt(0);){if(i++,i===g&amp;&amp;i++,i===x&amp;&amp;i++,i&gt;v){if(h)throw&quot;Bad character: &quot;+t;i=y,h=!0}a+=1e5}return a}function m(t){var s;switch(t){case&quot;C&quot;:s=11e5;break;case&quot;D&quot;:s=2e6;break;case&quot;E&quot;:s=28e5;break;case&quot;F&quot;:s=37e5;break;case&quot;G&quot;:s=46e5;break;case&quot;H&quot;:s=55e5;break;case&quot;J&quot;:s=64e5;break;case&quot;K&quot;:s=73e5;break;case&quot;L&quot;:s=82e5;break;case&quot;M&quot;:s=91e5;break;case&quot;N&quot;:s=0;break;case&quot;P&quot;:s=8e5;break;case&quot;Q&quot;:s=17e5;break;case&quot;R&quot;:s=26e5;break;case&quot;S&quot;:s=35e5;break;case&quot;T&quot;:s=44e5;break;case&quot;U&quot;:s=53e5;break;case&quot;V&quot;:s=62e5;break;case&quot;W&quot;:s=7e6;break;case&quot;X&quot;:s=79e5;break;default:s=-1}if(s&gt;=0)return s;throw&quot;Invalid zone letter: &quot;+t}var p=6,_=&quot;AJSAJS&quot;,d=&quot;AFAFAF&quot;,y=65,g=73,x=79,v=86,P=90;s.forward=function(t,s){return s=s||5,r(h({lat:t.lat,lon:t.lon}),s)},s.inverse=function(t){var s=e(c(t.toUpperCase()));return[s.left,s.bottom,s.right,s.top]}}),i(&quot;proj4/Point&quot;,[&quot;./mgrs&quot;],function(t){function s(t,i,a){if(!(this instanceof s))return new s(t,i,a);if(&quot;object&quot;==typeof t)this.x=t[0],this.y=t[1],this.z=t[2]||0;else if(&quot;string&quot;==typeof t&amp;&amp;&quot;undefined&quot;==typeof i){var h=t.split(&quot;,&quot;);this.x=parseFloat(h[0]),this.y=parseFloat(h[1]),this.z=parseFloat(h[2])||0}else this.x=t,this.y=i,this.z=a||0;this.clone=function(){return new s(this.x,this.y,this.z)},this.toString=function(){return&quot;x=&quot;+this.x+&quot;,y=&quot;+this.y},this.toShortString=function(){return this.x+&quot;, &quot;+this.y}}return s.fromMGRS=function(i){var a=t.inverse(i);return new s((a[2]+a[0])/2,(a[3]+a[1])/2)},s.prototype.toMGRS=function(s){return t.forward({lon:this.x,lat:this.y},s)},s}),i(&quot;proj4/extend&quot;,[],function(){return function(t,s){t=t||{};var i,a;if(!s)return t;for(a in s)i=s[a],void 0!==i&amp;&amp;(t[a]=i);return t}}),i(&quot;proj4/common&quot;,[],function(){var t={PI:3.141592653589793,HALF_PI:1.5707963267948966,TWO_PI:6.283185307179586,FORTPI:.7853981633974483,R2D:57.29577951308232,D2R:.017453292519943295,SEC_TO_RAD:484813681109536e-20,EPSLN:1e-10,MAX_ITER:20,COS_67P5:.3826834323650898,AD_C:1.0026,PJD_UNKNOWN:0,PJD_3PARAM:1,PJD_7PARAM:2,PJD_GRIDSHIFT:3,PJD_WGS84:4,PJD_NODATUM:5,SRS_WGS84_SEMIMAJOR:6378137,SRS_WGS84_ESQUARED:.006694379990141316,SIXTH:.16666666666666666,RA4:.04722222222222222,RA6:.022156084656084655,RV4:.06944444444444445,RV6:.04243827160493827,msfnz:function(t,s,i){var a=t*s;return i/Math.sqrt(1-a*a)},tsfnz:function(t,s,i){var a=t*i,h=.5*t;return a=Math.pow((1-a)/(1+a),h),Math.tan(.5*(this.HALF_PI-s))/a},phi2z:function(t,s){for(var i,a,h=.5*t,e=this.HALF_PI-2*Math.atan(s),n=0;15&gt;=n;n++)if(i=t*Math.sin(e),a=this.HALF_PI-2*Math.atan(s*Math.pow((1-i)/(1+i),h))-e,e+=a,Math.abs(a)&lt;=1e-10)return e;return-9999},qsfnz:function(t,s){var i;return t&gt;1e-7?(i=t*s,(1-t*t)*(s/(1-i*i)-.5/t*Math.log((1-i)/(1+i)))):2*s},iqsfnz:function(s,i){var a=1-(1-s*s)/(2*s)*Math.log((1-s)/(1+s));if(Math.abs(Math.abs(i)-a)&lt;1e-6)return 0&gt;i?-1*t.HALF_PI:t.HALF_PI;for(var h,e,n,r,o=Math.asin(.5*i),l=0;30&gt;l;l++)if(e=Math.sin(o),n=Math.cos(o),r=s*e,h=Math.pow(1-r*r,2)/(2*n)*(i/(1-s*s)-e/(1-r*r)+.5/s*Math.log((1-r)/(1+r))),o+=h,Math.abs(h)&lt;=1e-10)return o;return 0/0},asinz:function(t){return Math.abs(t)&gt;1&amp;&amp;(t=t&gt;1?1:-1),Math.asin(t)},e0fn:function(t){return 1-.25*t*(1+t/16*(3+1.25*t))},e1fn:function(t){return.375*t*(1+.25*t*(1+.46875*t))},e2fn:function(t){return.05859375*t*t*(1+.75*t)},e3fn:function(t){return t*t*t*(35/3072)},mlfn:function(t,s,i,a,h){return t*h-s*Math.sin(2*h)+i*Math.sin(4*h)-a*Math.sin(6*h)},imlfn:function(t,s,i,a,h){var e,n;e=t/s;for(var r=0;15&gt;r;r++)if(n=(t-(s*e-i*Math.sin(2*e)+a*Math.sin(4*e)-h*Math.sin(6*e)))/(s-2*i*Math.cos(2*e)+4*a*Math.cos(4*e)-6*h*Math.cos(6*e)),e+=n,Math.abs(n)&lt;=1e-10)return e;return 0/0},srat:function(t,s){return Math.pow((1-t)/(1+t),s)},sign:function(t){return 0&gt;t?-1:1},adjust_lon:function(t){return t=Math.abs(t)&lt;this.PI?t:t-this.sign(t)*this.TWO_PI},adjust_lat:function(t){return t=Math.abs(t)&lt;this.HALF_PI?t:t-this.sign(t)*this.PI},latiso:function(t,s,i){if(Math.abs(s)&gt;this.HALF_PI)return Number.NaN;if(s===this.HALF_PI)return Number.POSITIVE_INFINITY;if(s===-1*this.HALF_PI)return Number.NEGATIVE_INFINITY;var a=t*i;return Math.log(Math.tan((this.HALF_PI+s)/2))+t*Math.log((1-a)/(1+a))/2},fL:function(t,s){return 2*Math.atan(t*Math.exp(s))-this.HALF_PI},invlatiso:function(t,s){var i=this.fL(1,s),a=0,h=0;do a=i,h=t*Math.sin(a),i=this.fL(Math.exp(t*Math.log((1+h)/(1-h))/2),s);while(Math.abs(i-a)&gt;1e-12);return i},sinh:function(t){var s=Math.exp(t);return s=(s-1/s)/2},cosh:function(t){var s=Math.exp(t);return s=(s+1/s)/2},tanh:function(t){var s=Math.exp(t);return s=(s-1/s)/(s+1/s)},asinh:function(t){var s=t&gt;=0?1:-1;return s*Math.log(Math.abs(t)+Math.sqrt(t*t+1))},acosh:function(t){return 2*Math.log(Math.sqrt((t+1)/2)+Math.sqrt((t-1)/2))},atanh:function(t){return Math.log((t-1)/(t+1))/2},gN:function(t,s,i){var a=s*i;return t/Math.sqrt(1-a*a)},pj_enfn:function(t){var s=[];s[0]=this.C00-t*(this.C02+t*(this.C04+t*(this.C06+t*this.C08))),s[1]=t*(this.C22-t*(this.C04+t*(this.C06+t*this.C08)));var i=t*t;return s[2]=i*(this.C44-t*(this.C46+t*this.C48)),i*=t,s[3]=i*(this.C66-t*this.C68),s[4]=i*t*this.C88,s},pj_mlfn:function(t,s,i,a){return i*=s,s*=s,a[0]*t-i*(a[1]+s*(a[2]+s*(a[3]+s*a[4])))},pj_inv_mlfn:function(s,i,a){for(var h=1/(1-i),e=s,n=t.MAX_ITER;n;--n){var r=Math.sin(e),o=1-i*r*r;if(o=(this.pj_mlfn(e,r,Math.cos(e),a)-s)*o*Math.sqrt(o)*h,e-=o,Math.abs(o)&lt;t.EPSLN)return e}return e},nad_intr:function(t,s){var i,a={x:(t.x-1e-7)/s.del[0],y:(t.y-1e-7)/s.del[1]},h={x:Math.floor(a.x),y:Math.floor(a.y)},e={x:a.x-1*h.x,y:a.y-1*h.y},n={x:Number.NaN,y:Number.NaN};if(h.x&lt;0){if(!(-1===h.x&amp;&amp;e.x&gt;.99999999999))return n;h.x++,e.x=0}else if(i=h.x+1,i&gt;=s.lim[0]){if(!(i===s.lim[0]&amp;&amp;e.x&lt;1e-11))return n;h.x--,e.x=1}if(h.y&lt;0){if(!(-1===h.y&amp;&amp;e.y&gt;.99999999999))return n;h.y++,e.y=0}else if(i=h.y+1,i&gt;=s.lim[1]){if(!(i===s.lim[1]&amp;&amp;e.y&lt;1e-11))return n;h.y++,e.y=1}i=h.y*s.lim[0]+h.x;var r={x:s.cvs[i][0],y:s.cvs[i][1]};i++;var o={x:s.cvs[i][0],y:s.cvs[i][1]};i+=s.lim[0];var l={x:s.cvs[i][0],y:s.cvs[i][1]};i--;var u={x:s.cvs[i][0],y:s.cvs[i][1]},c=e.x*e.y,M=e.x*(1-e.y),f=(1-e.x)*(1-e.y),m=(1-e.x)*e.y;return n.x=f*r.x+M*o.x+m*u.x+c*l.x,n.y=f*r.y+M*o.y+m*u.y+c*l.y,n},nad_cvt:function(s,i,a){var h={x:Number.NaN,y:Number.NaN};if(isNaN(s.x))return h;var e={x:s.x,y:s.y};e.x-=a.ll[0],e.y-=a.ll[1],e.x=t.adjust_lon(e.x-t.PI)+t.PI;var n=t.nad_intr(e,a);if(i){if(isNaN(n.x))return h;n.x=e.x+n.x,n.y=e.y-n.y;var r,o,l=9,u=1e-12;do{if(o=t.nad_intr(n,a),isNaN(o.x)){this.reportError(&quot;Inverse grid shift iteration failed, presumably at grid edge.  Using first approximation.&quot;);break}r={x:n.x-o.x-e.x,y:n.y+o.y-e.y},n.x-=r.x,n.y-=r.y}while(l--&amp;&amp;Math.abs(r.x)&gt;u&amp;&amp;Math.abs(r.y)&gt;u);if(0&gt;l)return this.reportError(&quot;Inverse grid shift iterator failed to converge.&quot;),h;h.x=t.adjust_lon(n.x+a.ll[0]),h.y=n.y+a.ll[1]}else isNaN(n.x)||(h.x=s.x-n.x,h.y=s.y+n.y);return h},C00:1,C02:.25,C04:.046875,C06:.01953125,C08:.01068115234375,C22:.75,C44:.46875,C46:.013020833333333334,C48:.007120768229166667,C66:.3645833333333333,C68:.005696614583333333,C88:.3076171875};return t}),i(&quot;proj4/constants&quot;,[],function(){var t={};return t.PrimeMeridian={greenwich:0,lisbon:-9.131906111111,paris:2.337229166667,bogota:-74.080916666667,madrid:-3.687938888889,rome:12.452333333333,bern:7.439583333333,jakarta:106.807719444444,ferro:-17.666666666667,brussels:4.367975,stockholm:18.058277777778,athens:23.7163375,oslo:10.722916666667},t.Ellipsoid={MERIT:{a:6378137,rf:298.257,ellipseName:&quot;MERIT 1983&quot;},SGS85:{a:6378136,rf:298.257,ellipseName:&quot;Soviet Geodetic System 85&quot;},GRS80:{a:6378137,rf:298.257222101,ellipseName:&quot;GRS 1980(IUGG, 1980)&quot;},IAU76:{a:6378140,rf:298.257,ellipseName:&quot;IAU 1976&quot;},airy:{a:6377563.396,b:6356256.91,ellipseName:&quot;Airy 1830&quot;},&quot;APL4.&quot;:{a:6378137,rf:298.25,ellipseName:&quot;Appl. Physics. 1965&quot;},NWL9D:{a:6378145,rf:298.25,ellipseName:&quot;Naval Weapons Lab., 1965&quot;},mod_airy:{a:6377340.189,b:6356034.446,ellipseName:&quot;Modified Airy&quot;},andrae:{a:6377104.43,rf:300,ellipseName:&quot;Andrae 1876 (Den., Iclnd.)&quot;},aust_SA:{a:6378160,rf:298.25,ellipseName:&quot;Australian Natl &amp; S. Amer. 1969&quot;},GRS67:{a:6378160,rf:298.247167427,ellipseName:&quot;GRS 67(IUGG 1967)&quot;},bessel:{a:6377397.155,rf:299.1528128,ellipseName:&quot;Bessel 1841&quot;},bess_nam:{a:6377483.865,rf:299.1528128,ellipseName:&quot;Bessel 1841 (Namibia)&quot;},clrk66:{a:6378206.4,b:6356583.8,ellipseName:&quot;Clarke 1866&quot;},clrk80:{a:6378249.145,rf:293.4663,ellipseName:&quot;Clarke 1880 mod.&quot;},clrk58:{a:6378293.645208759,rf:294.2606763692654,ellipseName:&quot;Clarke 1858&quot;},CPM:{a:6375738.7,rf:334.29,ellipseName:&quot;Comm. des Poids et Mesures 1799&quot;},delmbr:{a:6376428,rf:311.5,ellipseName:&quot;Delambre 1810 (Belgium)&quot;},engelis:{a:6378136.05,rf:298.2566,ellipseName:&quot;Engelis 1985&quot;},evrst30:{a:6377276.345,rf:300.8017,ellipseName:&quot;Everest 1830&quot;},evrst48:{a:6377304.063,rf:300.8017,ellipseName:&quot;Everest 1948&quot;},evrst56:{a:6377301.243,rf:300.8017,ellipseName:&quot;Everest 1956&quot;},evrst69:{a:6377295.664,rf:300.8017,ellipseName:&quot;Everest 1969&quot;},evrstSS:{a:6377298.556,rf:300.8017,ellipseName:&quot;Everest (Sabah &amp; Sarawak)&quot;},fschr60:{a:6378166,rf:298.3,ellipseName:&quot;Fischer (Mercury Datum) 1960&quot;},fschr60m:{a:6378155,rf:298.3,ellipseName:&quot;Fischer 1960&quot;},fschr68:{a:6378150,rf:298.3,ellipseName:&quot;Fischer 1968&quot;},helmert:{a:6378200,rf:298.3,ellipseName:&quot;Helmert 1906&quot;},hough:{a:6378270,rf:297,ellipseName:&quot;Hough&quot;},intl:{a:6378388,rf:297,ellipseName:&quot;International 1909 (Hayford)&quot;},kaula:{a:6378163,rf:298.24,ellipseName:&quot;Kaula 1961&quot;},lerch:{a:6378139,rf:298.257,ellipseName:&quot;Lerch 1979&quot;},mprts:{a:6397300,rf:191,ellipseName:&quot;Maupertius 1738&quot;},new_intl:{a:6378157.5,b:6356772.2,ellipseName:&quot;New International 1967&quot;},plessis:{a:6376523,rf:6355863,ellipseName:&quot;Plessis 1817 (France)&quot;},krass:{a:6378245,rf:298.3,ellipseName:&quot;Krassovsky, 1942&quot;},SEasia:{a:6378155,b:6356773.3205,ellipseName:&quot;Southeast Asia&quot;},walbeck:{a:6376896,b:6355834.8467,ellipseName:&quot;Walbeck&quot;},WGS60:{a:6378165,rf:298.3,ellipseName:&quot;WGS 60&quot;},WGS66:{a:6378145,rf:298.25,ellipseName:&quot;WGS 66&quot;},WGS72:{a:6378135,rf:298.26,ellipseName:&quot;WGS 72&quot;},WGS84:{a:6378137,rf:298.257223563,ellipseName:&quot;WGS 84&quot;},sphere:{a:6370997,b:6370997,ellipseName:&quot;Normal Sphere (r=6370997)&quot;}},t.Datum={wgs84:{towgs84:&quot;0,0,0&quot;,ellipse:&quot;WGS84&quot;,datumName:&quot;WGS84&quot;},ch1903:{towgs84:&quot;674.374,15.056,405.346&quot;,ellipse:&quot;bessel&quot;,datumName:&quot;swiss&quot;},ggrs87:{towgs84:&quot;-199.87,74.79,246.62&quot;,ellipse:&quot;GRS80&quot;,datumName:&quot;Greek_Geodetic_Reference_System_1987&quot;},nad83:{towgs84:&quot;0,0,0&quot;,ellipse:&quot;GRS80&quot;,datumName:&quot;North_American_Datum_1983&quot;},nad27:{nadgrids:&quot;@conus,@alaska,@ntv2_0.gsb,@ntv1_can.dat&quot;,ellipse:&quot;clrk66&quot;,datumName:&quot;North_American_Datum_1927&quot;},potsdam:{towgs84:&quot;606.0,23.0,413.0&quot;,ellipse:&quot;bessel&quot;,datumName:&quot;Potsdam Rauenberg 1950 DHDN&quot;},carthage:{towgs84:&quot;-263.0,6.0,431.0&quot;,ellipse:&quot;clark80&quot;,datumName:&quot;Carthage 1934 Tunisia&quot;},hermannskogel:{towgs84:&quot;653.0,-212.0,449.0&quot;,ellipse:&quot;bessel&quot;,datumName:&quot;Hermannskogel&quot;},ire65:{towgs84:&quot;482.530,-130.596,564.557,-1.042,-0.214,-0.631,8.15&quot;,ellipse:&quot;mod_airy&quot;,datumName:&quot;Ireland 1965&quot;},rassadiran:{towgs84:&quot;-133.63,-157.5,-158.62&quot;,ellipse:&quot;intl&quot;,datumName:&quot;Rassadiran&quot;},nzgd49:{towgs84:&quot;59.47,-5.04,187.44,0.47,-0.1,1.024,-4.5993&quot;,ellipse:&quot;intl&quot;,datumName:&quot;New Zealand Geodetic Datum 1949&quot;},osgb36:{towgs84:&quot;446.448,-125.157,542.060,0.1502,0.2470,0.8421,-20.4894&quot;,ellipse:&quot;airy&quot;,datumName:&quot;Airy 1830&quot;},s_jtsk:{towgs84:&quot;589,76,480&quot;,ellipse:&quot;bessel&quot;,datumName:&quot;S-JTSK (Ferro)&quot;},beduaram:{towgs84:&quot;-106,-87,188&quot;,ellipse:&quot;clrk80&quot;,datumName:&quot;Beduaram&quot;},gunung_segara:{towgs84:&quot;-403,684,41&quot;,ellipse:&quot;bessel&quot;,datumName:&quot;Gunung Segara Jakarta&quot;}},t.Datum.OSB36=t.Datum.OSGB36,t.wktProjections={&quot;Lambert Tangential Conformal Conic Projection&quot;:&quot;lcc&quot;,Lambert_Conformal_Conic:&quot;lcc&quot;,Lambert_Conformal_Conic_2SP:&quot;lcc&quot;,Mercator:&quot;merc&quot;,&quot;Popular Visualisation Pseudo Mercator&quot;:&quot;merc&quot;,Mercator_1SP:&quot;merc&quot;,Transverse_Mercator:&quot;tmerc&quot;,&quot;Transverse Mercator&quot;:&quot;tmerc&quot;,&quot;Lambert Azimuthal Equal Area&quot;:&quot;laea&quot;,&quot;Universal Transverse Mercator System&quot;:&quot;utm&quot;,Hotine_Oblique_Mercator:&quot;omerc&quot;,&quot;Hotine Oblique Mercator&quot;:&quot;omerc&quot;,Hotine_Oblique_Mercator_Azimuth_Natural_Origin:&quot;omerc&quot;,Hotine_Oblique_Mercator_Azimuth_Center:&quot;omerc&quot;,Van_der_Grinten_I:&quot;vandg&quot;,VanDerGrinten:&quot;vandg&quot;,Stereographic_North_Pole:&quot;sterea&quot;,Oblique_Stereographic:&quot;sterea&quot;,Polar_Stereographic:&quot;sterea&quot;,Polyconic:&quot;poly&quot;,New_Zealand_Map_Grid:&quot;nzmg&quot;,Miller_Cylindrical:&quot;mill&quot;,Krovak:&quot;krovak&quot;,Equirectangular:&quot;eqc&quot;,Equidistant_Cylindrical:&quot;eqc&quot;,Cassini:&quot;cass&quot;,Cassini_Soldner:&quot;cass&quot;,Azimuthal_Equidistant:&quot;aeqd&quot;,Albers_Conic_Equal_Area:&quot;aea&quot;,Albers:&quot;aea&quot;,Mollweide:&quot;moll&quot;,Lambert_Azimuthal_Equal_Area:&quot;laea&quot;,Sinusoidal:&quot;sinu&quot;,Equidistant_Conic:&quot;eqdc&quot;,Mercator_Auxiliary_Sphere:&quot;merc&quot;},t.grids={&quot;null&quot;:{ll:[-3.14159265,-1.57079633],del:[3.14159265,1.57079633],lim:[3,3],count:9,cvs:[[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0]]}},t}),i(&quot;proj4/global&quot;,[],function(){return function(t){t(&quot;WGS84&quot;,&quot;+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees&quot;),t(&quot;EPSG:4326&quot;,&quot;+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees&quot;),t(&quot;EPSG:4269&quot;,&quot;+title=NAD83 (long/lat) +proj=longlat +a=6378137.0 +b=6356752.31414036 +ellps=GRS80 +datum=NAD83 +units=degrees&quot;),t(&quot;EPSG:3857&quot;,&quot;+title=WGS 84 / Pseudo-Mercator +proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs&quot;),t[&quot;EPSG:3785&quot;]=t[&quot;EPSG:3857&quot;],t.GOOGLE=t[&quot;EPSG:3857&quot;],t[&quot;EPSG:900913&quot;]=t[&quot;EPSG:3857&quot;],t[&quot;EPSG:102113&quot;]=t[&quot;EPSG:3857&quot;]}}),i(&quot;proj4/projString&quot;,[&quot;./common&quot;,&quot;./constants&quot;],function(t,s){return function(i){var a={},h={};i.split(&quot;+&quot;).map(function(t){return t.trim()}).filter(function(t){return t}).forEach(function(t){var s=t.split(&quot;=&quot;);&quot;@null&quot;!==s[1]&amp;&amp;(s.push(!0),h[s[0].toLowerCase()]=s[1])});var e,n,r,o={proj:&quot;projName&quot;,datum:&quot;datumCode&quot;,rf:function(t){a.rf=parseFloat(t,10)},lat_0:function(s){a.lat0=s*t.D2R},lat_1:function(s){a.lat1=s*t.D2R},lat_2:function(s){a.lat2=s*t.D2R},lat_ts:function(s){a.lat_ts=s*t.D2R},lon_0:function(s){a.long0=s*t.D2R},lon_1:function(s){a.long1=s*t.D2R},lon_2:function(s){a.long2=s*t.D2R},alpha:function(s){a.alpha=parseFloat(s)*t.D2R},lonc:function(s){a.longc=s*t.D2R},x_0:function(t){a.x0=parseFloat(t,10)},y_0:function(t){a.y0=parseFloat(t,10)},k_0:function(t){a.k0=parseFloat(t,10)},k:function(t){a.k0=parseFloat(t,10)},r_a:function(){a.R_A=!0},zone:function(t){a.zone=parseInt(t,10)},south:function(){a.utmSouth=!0},towgs84:function(t){a.datum_params=t.split(&quot;,&quot;).map(function(t){return parseFloat(t,10)})},to_meter:function(t){a.to_meter=parseFloat(t,10)},from_greenwich:function(s){a.from_greenwich=s*t.D2R},pm:function(i){a.from_greenwich=(s.PrimeMeridian[i]?s.PrimeMeridian[i]:parseFloat(i,10))*t.D2R},axis:function(t){var s=&quot;ewnsud&quot;;3===t.length&amp;&amp;-1!==s.indexOf(t.substr(0,1))&amp;&amp;-1!==s.indexOf(t.substr(1,1))&amp;&amp;-1!==s.indexOf(t.substr(2,1))&amp;&amp;(a.axis=t)}};for(e in h)n=h[e],e in o?(r=o[e],&quot;function&quot;==typeof r?r(n):a[r]=n):a[e]=n;return a}}),i(&quot;proj4/wkt&quot;,[&quot;./extend&quot;,&quot;./constants&quot;,&quot;./common&quot;],function(t,s,i){function a(s,i,a){s[i]=a.map(function(t){var s={};return h(t,s),s}).reduce(function(s,i){return t(s,i)},{})}function h(t,s){var i;return Array.isArray(t)?(i=t.shift(),&quot;PARAMETER&quot;===i&amp;&amp;(i=t.shift()),1===t.length?Array.isArray(t[0])?(s[i]={},h(t[0],s[i])):s[i]=t[0]:t.length?&quot;TOWGS84&quot;===i?s[i]=t:(s[i]={},[&quot;UNIT&quot;,&quot;PRIMEM&quot;,&quot;VERT_DATUM&quot;].indexOf(i)&gt;-1?(s[i]={name:t[0].toLowerCase(),convert:t[1]},3===t.length&amp;&amp;(s[i].auth=t[2])):&quot;SPHEROID&quot;===i?(s[i]={name:t[0],a:t[1],rf:t[2]},4===t.length&amp;&amp;(s[i].auth=t[3])):[&quot;GEOGCS&quot;,&quot;GEOCCS&quot;,&quot;DATUM&quot;,&quot;VERT_CS&quot;,&quot;COMPD_CS&quot;,&quot;LOCAL_CS&quot;,&quot;FITTED_CS&quot;,&quot;LOCAL_DATUM&quot;].indexOf(i)&gt;-1?(t[0]=[&quot;name&quot;,t[0]],a(s,i,t)):t.every(function(t){return Array.isArray(t)})?a(s,i,t):h(t,s[i])):s[i]=!0,void 0):(s[t]=!0,void 0)}function e(t,s){var i=s[0],a=s[1];!(i in t)&amp;&amp;a in t&amp;&amp;(t[i]=t[a],3===s.length&amp;&amp;(t[i]=s[2](t[i])))}function n(t){return t*i.D2R}function r(t){function i(s){var i=t.to_meter||1;return parseFloat(s,10)*i}&quot;GEOGCS&quot;===t.type?t.projName=&quot;longlat&quot;:&quot;LOCAL_CS&quot;===t.type?(t.projName=&quot;identity&quot;,t.local=!0):t.projName=s.wktProjections[t.PROJECTION],t.UNIT&amp;&amp;(t.units=t.UNIT.name.toLowerCase(),&quot;metre&quot;===t.units&amp;&amp;(t.units=&quot;meter&quot;),t.UNIT.convert&amp;&amp;(t.to_meter=parseFloat(t.UNIT.convert,10))),t.GEOGCS&amp;&amp;(t.datumCode=t.GEOGCS.DATUM?t.GEOGCS.DATUM.name.toLowerCase():t.GEOGCS.name.toLowerCase(),&quot;d_&quot;===t.datumCode.slice(0,2)&amp;&amp;(t.datumCode=t.datumCode.slice(2)),(&quot;new_zealand_geodetic_datum_1949&quot;===t.datumCode||&quot;new_zealand_1949&quot;===t.datumCode)&amp;&amp;(t.datumCode=&quot;nzgd49&quot;),&quot;wgs_1984&quot;===t.datumCode&amp;&amp;(&quot;Mercator_Auxiliary_Sphere&quot;===t.PROJECTION&amp;&amp;(t.sphere=!0),t.datumCode=&quot;wgs84&quot;),&quot;_ferro&quot;===t.datumCode.slice(-6)&amp;&amp;(t.datumCode=t.datumCode.slice(0,-6)),&quot;_jakarta&quot;===t.datumCode.slice(-8)&amp;&amp;(t.datumCode=t.datumCode.slice(0,-8)),t.GEOGCS.DATUM&amp;&amp;t.GEOGCS.DATUM.SPHEROID&amp;&amp;(t.ellps=t.GEOGCS.DATUM.SPHEROID.name.replace(&quot;_19&quot;,&quot;&quot;).replace(/[Cc]larke\_18/,&quot;clrk&quot;),&quot;international&quot;===t.ellps.toLowerCase().slice(0,13)&amp;&amp;(t.ellps=&quot;intl&quot;),t.a=t.GEOGCS.DATUM.SPHEROID.a,t.rf=parseFloat(t.GEOGCS.DATUM.SPHEROID.rf,10))),t.b&amp;&amp;!isFinite(t.b)&amp;&amp;(t.b=t.a);var a=function(s){return e(t,s)},h=[[&quot;standard_parallel_1&quot;,&quot;Standard_Parallel_1&quot;],[&quot;standard_parallel_2&quot;,&quot;Standard_Parallel_2&quot;],[&quot;false_easting&quot;,&quot;False_Easting&quot;],[&quot;false_northing&quot;,&quot;False_Northing&quot;],[&quot;central_meridian&quot;,&quot;Central_Meridian&quot;],[&quot;latitude_of_origin&quot;,&quot;Latitude_Of_Origin&quot;],[&quot;scale_factor&quot;,&quot;Scale_Factor&quot;],[&quot;k0&quot;,&quot;scale_factor&quot;],[&quot;latitude_of_center&quot;,&quot;Latitude_of_center&quot;],[&quot;lat0&quot;,&quot;latitude_of_center&quot;,n],[&quot;longitude_of_center&quot;,&quot;Longitude_Of_Center&quot;],[&quot;longc&quot;,&quot;longitude_of_center&quot;,n],[&quot;x0&quot;,&quot;false_easting&quot;,i],[&quot;y0&quot;,&quot;false_northing&quot;,i],[&quot;long0&quot;,&quot;central_meridian&quot;,n],[&quot;lat0&quot;,&quot;latitude_of_origin&quot;,n],[&quot;lat0&quot;,&quot;standard_parallel_1&quot;,n],[&quot;lat1&quot;,&quot;standard_parallel_1&quot;,n],[&quot;lat2&quot;,&quot;standard_parallel_2&quot;,n],[&quot;alpha&quot;,&quot;azimuth&quot;,n],[&quot;srsCode&quot;,&quot;name&quot;]];h.forEach(a),t.long0||!t.longc||&quot;Albers_Conic_Equal_Area&quot;!==t.PROJECTION&amp;&amp;&quot;Lambert_Azimuthal_Equal_Area&quot;!==t.PROJECTION||(t.long0=t.longc)}return function(s,i){var a=JSON.parse((&quot;,&quot;+s).replace(/\,([A-Z_0-9]+?)(\[)/g,&#39;,[&quot;$1&quot;,&#39;).slice(1).replace(/\,([A-Z_0-9]+?)\]/g,&#39;,&quot;$1&quot;]&#39;)),e=a.shift(),n=a.shift();a.unshift([&quot;name&quot;,n]),a.unshift([&quot;type&quot;,e]),a.unshift(&quot;output&quot;);var o={};return h(a,o),r(o.output),t(i,o.output)}}),i(&quot;proj4/defs&quot;,[&quot;./common&quot;,&quot;./constants&quot;,&quot;./global&quot;,&quot;./projString&quot;,&quot;./wkt&quot;],function(t,s,i,a,h){function e(t){var s=this;if(2===arguments.length)e[t]=&quot;+&quot;===arguments[1][0]?a(arguments[1]):h(arguments[1]);else if(1===arguments.length)return Array.isArray(t)?t.map(function(t){Array.isArray(t)?e.apply(s,t):e(t)}):(&quot;string&quot;==typeof t||(&quot;EPSG&quot;in t?e[&quot;EPSG:&quot;+t.EPSG]=t:&quot;ESRI&quot;in t?e[&quot;ESRI:&quot;+t.ESRI]=t:&quot;IAU2000&quot;in t?e[&quot;IAU2000:&quot;+t.IAU2000]=t:console.log(t)),void 0)}return i(e),e}),i(&quot;proj4/datum&quot;,[&quot;./common&quot;],function(t){var s=function(i){if(!(this instanceof s))return new s(i);if(this.datum_type=t.PJD_WGS84,i){if(i.datumCode&amp;&amp;&quot;none&quot;===i.datumCode&amp;&amp;(this.datum_type=t.PJD_NODATUM),i.datum_params){for(var a=0;a&lt;i.datum_params.length;a++)i.datum_params[a]=parseFloat(i.datum_params[a]);(0!==i.datum_params[0]||0!==i.datum_params[1]||0!==i.datum_params[2])&amp;&amp;(this.datum_type=t.PJD_3PARAM),i.datum_params.length&gt;3&amp;&amp;(0!==i.datum_params[3]||0!==i.datum_params[4]||0!==i.datum_params[5]||0!==i.datum_params[6])&amp;&amp;(this.datum_type=t.PJD_7PARAM,i.datum_params[3]*=t.SEC_TO_RAD,i.datum_params[4]*=t.SEC_TO_RAD,i.datum_params[5]*=t.SEC_TO_RAD,i.datum_params[6]=i.datum_params[6]/1e6+1)}this.datum_type=i.grids?t.PJD_GRIDSHIFT:this.datum_type,this.a=i.a,this.b=i.b,this.es=i.es,this.ep2=i.ep2,this.datum_params=i.datum_params,this.datum_type===t.PJD_GRIDSHIFT&amp;&amp;(this.grids=i.grids)}};return s.prototype={compare_datums:function(s){return this.datum_type!==s.datum_type?!1:this.a!==s.a||Math.abs(this.es-s.es)&gt;5e-11?!1:this.datum_type===t.PJD_3PARAM?this.datum_params[0]===s.datum_params[0]&amp;&amp;this.datum_params[1]===s.datum_params[1]&amp;&amp;this.datum_params[2]===s.datum_params[2]:this.datum_type===t.PJD_7PARAM?this.datum_params[0]===s.datum_params[0]&amp;&amp;this.datum_params[1]===s.datum_params[1]&amp;&amp;this.datum_params[2]===s.datum_params[2]&amp;&amp;this.datum_params[3]===s.datum_params[3]&amp;&amp;this.datum_params[4]===s.datum_params[4]&amp;&amp;this.datum_params[5]===s.datum_params[5]&amp;&amp;this.datum_params[6]===s.datum_params[6]:this.datum_type===t.PJD_GRIDSHIFT||s.datum_type===t.PJD_GRIDSHIFT?this.nadgrids===s.nadgrids:!0},geodetic_to_geocentric:function(s){var i,a,h,e,n,r,o,l=s.x,u=s.y,c=s.z?s.z:0,M=0;if(u&lt;-t.HALF_PI&amp;&amp;u&gt;-1.001*t.HALF_PI)u=-t.HALF_PI;else if(u&gt;t.HALF_PI&amp;&amp;u&lt;1.001*t.HALF_PI)u=t.HALF_PI;else if(u&lt;-t.HALF_PI||u&gt;t.HALF_PI)return null;return l&gt;t.PI&amp;&amp;(l-=2*t.PI),n=Math.sin(u),o=Math.cos(u),r=n*n,e=this.a/Math.sqrt(1-this.es*r),i=(e+c)*o*Math.cos(l),a=(e+c)*o*Math.sin(l),h=(e*(1-this.es)+c)*n,s.x=i,s.y=a,s.z=h,M},geocentric_to_geodetic:function(s){var i,a,h,e,n,r,o,l,u,c,M,f,m,p,_,d,y,g=1e-12,x=g*g,v=30,P=s.x,b=s.y,C=s.z?s.z:0;if(m=!1,i=Math.sqrt(P*P+b*b),a=Math.sqrt(P*P+b*b+C*C),i/this.a&lt;g){if(m=!0,_=0,a/this.a&lt;g)return d=t.HALF_PI,y=-this.b,void 0}else _=Math.atan2(b,P);h=C/a,e=i/a,n=1/Math.sqrt(1-this.es*(2-this.es)*e*e),l=e*(1-this.es)*n,u=h*n,p=0;do p++,o=this.a/Math.sqrt(1-this.es*u*u),y=i*l+C*u-o*(1-this.es*u*u),r=this.es*o/(o+y),n=1/Math.sqrt(1-r*(2-r)*e*e),c=e*(1-r)*n,M=h*n,f=M*l-c*u,l=c,u=M;while(f*f&gt;x&amp;&amp;v&gt;p);return d=Math.atan(M/Math.abs(c)),s.x=_,s.y=d,s.z=y,s},geocentric_to_geodetic_noniter:function(s){var i,a,h,e,n,r,o,l,u,c,M,f,m,p,_,d,y,g=s.x,x=s.y,v=s.z?s.z:0;if(g=parseFloat(g),x=parseFloat(x),v=parseFloat(v),y=!1,0!==g)i=Math.atan2(x,g);else if(x&gt;0)i=t.HALF_PI;else if(0&gt;x)i=-t.HALF_PI;else if(y=!0,i=0,v&gt;0)a=t.HALF_PI;else{if(!(0&gt;v))return a=t.HALF_PI,h=-this.b,void 0;a=-t.HALF_PI}return n=g*g+x*x,e=Math.sqrt(n),r=v*t.AD_C,l=Math.sqrt(r*r+n),c=r/l,f=e/l,M=c*c*c,o=v+this.b*this.ep2*M,d=e-this.a*this.es*f*f*f,u=Math.sqrt(o*o+d*d),m=o/u,p=d/u,_=this.a/Math.sqrt(1-this.es*m*m),h=p&gt;=t.COS_67P5?e/p-_:p&lt;=-t.COS_67P5?e/-p-_:v/m+_*(this.es-1),y===!1&amp;&amp;(a=Math.atan(m/p)),s.x=i,s.y=a,s.z=h,s},geocentric_to_wgs84:function(s){if(this.datum_type===t.PJD_3PARAM)s.x+=this.datum_params[0],s.y+=this.datum_params[1],s.z+=this.datum_params[2];else if(this.datum_type===t.PJD_7PARAM){var i=this.datum_params[0],a=this.datum_params[1],h=this.datum_params[2],e=this.datum_params[3],n=this.datum_params[4],r=this.datum_params[5],o=this.datum_params[6],l=o*(s.x-r*s.y+n*s.z)+i,u=o*(r*s.x+s.y-e*s.z)+a,c=o*(-n*s.x+e*s.y+s.z)+h;s.x=l,s.y=u,s.z=c}},geocentric_from_wgs84:function(s){if(this.datum_type===t.PJD_3PARAM)s.x-=this.datum_params[0],s.y-=this.datum_params[1],s.z-=this.datum_params[2];else if(this.datum_type===t.PJD_7PARAM){var i=this.datum_params[0],a=this.datum_params[1],h=this.datum_params[2],e=this.datum_params[3],n=this.datum_params[4],r=this.datum_params[5],o=this.datum_params[6],l=(s.x-i)/o,u=(s.y-a)/o,c=(s.z-h)/o;s.x=l+r*u-n*c,s.y=-r*l+u+e*c,s.z=n*l-e*u+c}}},s}),i(&quot;proj4/projCode/longlat&quot;,[&quot;require&quot;,&quot;exports&quot;,&quot;module&quot;],function(t,s){function i(t){return t}s.init=function(){},s.forward=i,s.inverse=i}),i(&quot;proj4/projCode/tmerc&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.e0=t.e0fn(this.es),this.e1=t.e1fn(this.es),this.e2=t.e2fn(this.es),this.e3=t.e3fn(this.es),this.ml0=this.a*t.mlfn(this.e0,this.e1,this.e2,this.e3,this.lat0)},forward:function(s){var i,a,h,e=s.x,n=s.y,r=t.adjust_lon(e-this.long0),o=Math.sin(n),l=Math.cos(n);if(this.sphere){var u=l*Math.sin(r);if(Math.abs(Math.abs(u)-1)&lt;1e-10)return 93;a=.5*this.a*this.k0*Math.log((1+u)/(1-u)),i=Math.acos(l*Math.cos(r)/Math.sqrt(1-u*u)),0&gt;n&amp;&amp;(i=-i),h=this.a*this.k0*(i-this.lat0)}else{var c=l*r,M=Math.pow(c,2),f=this.ep2*Math.pow(l,2),m=Math.tan(n),p=Math.pow(m,2);i=1-this.es*Math.pow(o,2);var _=this.a/Math.sqrt(i),d=this.a*t.mlfn(this.e0,this.e1,this.e2,this.e3,n);a=this.k0*_*c*(1+M/6*(1-p+f+M/20*(5-18*p+Math.pow(p,2)+72*f-58*this.ep2)))+this.x0,h=this.k0*(d-this.ml0+_*m*M*(.5+M/24*(5-p+9*f+4*Math.pow(f,2)+M/30*(61-58*p+Math.pow(p,2)+600*f-330*this.ep2))))+this.y0}return s.x=a,s.y=h,s},inverse:function(s){var i,a,h,e,n,r,o=6;if(this.sphere){var l=Math.exp(s.x/(this.a*this.k0)),u=.5*(l-1/l),c=this.lat0+s.y/(this.a*this.k0),M=Math.cos(c);i=Math.sqrt((1-M*M)/(1+u*u)),n=t.asinz(i),0&gt;c&amp;&amp;(n=-n),r=0===u&amp;&amp;0===M?this.long0:t.adjust_lon(Math.atan2(u,M)+this.long0)}else{var f=s.x-this.x0,m=s.y-this.y0;for(i=(this.ml0+m/this.k0)/this.a,a=i,e=0;!0&amp;&amp;(h=(i+this.e1*Math.sin(2*a)-this.e2*Math.sin(4*a)+this.e3*Math.sin(6*a))/this.e0-a,a+=h,!(Math.abs(h)&lt;=t.EPSLN));e++)if(e&gt;=o)return 95;if(Math.abs(a)&lt;t.HALF_PI){var p=Math.sin(a),_=Math.cos(a),d=Math.tan(a),y=this.ep2*Math.pow(_,2),g=Math.pow(y,2),x=Math.pow(d,2),v=Math.pow(x,2);i=1-this.es*Math.pow(p,2);var P=this.a/Math.sqrt(i),b=P*(1-this.es)/i,C=f/(P*this.k0),S=Math.pow(C,2);n=a-P*d*S/b*(.5-S/24*(5+3*x+10*y-4*g-9*this.ep2-S/30*(61+90*x+298*y+45*v-252*this.ep2-3*g))),r=t.adjust_lon(this.long0+C*(1-S/6*(1+2*x+y-S/20*(5-2*y+28*x-3*g+8*this.ep2+24*v)))/_)}else n=t.HALF_PI*t.sign(m),r=this.long0}return s.x=r,s.y=n,s}}}),i(&quot;proj4/projCode/utm&quot;,[&quot;../common&quot;,&quot;./tmerc&quot;],function(t,s){return{dependsOn:&quot;tmerc&quot;,init:function(){this.zone&amp;&amp;(this.lat0=0,this.long0=(6*Math.abs(this.zone)-183)*t.D2R,this.x0=5e5,this.y0=this.utmSouth?1e7:0,this.k0=.9996,s.init.apply(this),this.forward=s.forward,this.inverse=s.inverse)}}}),i(&quot;proj4/projCode/gauss&quot;,[&quot;../common&quot;],function(t){return{init:function(){var s=Math.sin(this.lat0),i=Math.cos(this.lat0);</td>
      </tr>
      <tr>
        <td id="L2" class="blob-num js-line-number" data-line-number="2"></td>
        <td id="LC2" class="blob-code blob-code-inner js-file-line">i*=i,this.rc=Math.sqrt(1-this.es)/(1-this.es*s*s),this.C=Math.sqrt(1+this.es*i*i/(1-this.es)),this.phic0=Math.asin(s/this.C),this.ratexp=.5*this.C*this.e,this.K=Math.tan(.5*this.phic0+t.FORTPI)/(Math.pow(Math.tan(.5*this.lat0+t.FORTPI),this.C)*t.srat(this.e*s,this.ratexp))},forward:function(s){var i=s.x,a=s.y;return s.y=2*Math.atan(this.K*Math.pow(Math.tan(.5*a+t.FORTPI),this.C)*t.srat(this.e*Math.sin(a),this.ratexp))-t.HALF_PI,s.x=this.C*i,s},inverse:function(s){for(var i=1e-14,a=s.x/this.C,h=s.y,e=Math.pow(Math.tan(.5*h+t.FORTPI)/this.K,1/this.C),n=t.MAX_ITER;n&gt;0&amp;&amp;(h=2*Math.atan(e*t.srat(this.e*Math.sin(s.y),-.5*this.e))-t.HALF_PI,!(Math.abs(h-s.y)&lt;i));--n)s.y=h;return n?(s.x=a,s.y=h,s):null}}}),i(&quot;proj4/projCode/sterea&quot;,[&quot;../common&quot;,&quot;./gauss&quot;],function(t,s){return{init:function(){s.init.apply(this),this.rc&amp;&amp;(this.sinc0=Math.sin(this.phic0),this.cosc0=Math.cos(this.phic0),this.R2=2*this.rc,this.title||(this.title=&quot;Oblique Stereographic Alternative&quot;))},forward:function(i){var a,h,e,n;return i.x=t.adjust_lon(i.x-this.long0),s.forward.apply(this,[i]),a=Math.sin(i.y),h=Math.cos(i.y),e=Math.cos(i.x),n=this.k0*this.R2/(1+this.sinc0*a+this.cosc0*h*e),i.x=n*h*Math.sin(i.x),i.y=n*(this.cosc0*a-this.sinc0*h*e),i.x=this.a*i.x+this.x0,i.y=this.a*i.y+this.y0,i},inverse:function(i){var a,h,e,n,r;if(i.x=(i.x-this.x0)/this.a,i.y=(i.y-this.y0)/this.a,i.x/=this.k0,i.y/=this.k0,r=Math.sqrt(i.x*i.x+i.y*i.y)){var o=2*Math.atan2(r,this.R2);a=Math.sin(o),h=Math.cos(o),n=Math.asin(h*this.sinc0+i.y*a*this.cosc0/r),e=Math.atan2(i.x*a,r*this.cosc0*h-i.y*this.sinc0*a)}else n=this.phic0,e=0;return i.x=e,i.y=n,s.inverse.apply(this,[i]),i.x=t.adjust_lon(i.x+this.long0),i}}}),i(&quot;proj4/projCode/somerc&quot;,[],function(){return{init:function(){var t=this.lat0;this.lambda0=this.long0;var s=Math.sin(t),i=this.a,a=this.rf,h=1/a,e=2*h-Math.pow(h,2),n=this.e=Math.sqrt(e);this.R=this.k0*i*Math.sqrt(1-e)/(1-e*Math.pow(s,2)),this.alpha=Math.sqrt(1+e/(1-e)*Math.pow(Math.cos(t),4)),this.b0=Math.asin(s/this.alpha);var r=Math.log(Math.tan(Math.PI/4+this.b0/2)),o=Math.log(Math.tan(Math.PI/4+t/2)),l=Math.log((1+n*s)/(1-n*s));this.K=r-this.alpha*o+this.alpha*n/2*l},forward:function(t){var s=Math.log(Math.tan(Math.PI/4-t.y/2)),i=this.e/2*Math.log((1+this.e*Math.sin(t.y))/(1-this.e*Math.sin(t.y))),a=-this.alpha*(s+i)+this.K,h=2*(Math.atan(Math.exp(a))-Math.PI/4),e=this.alpha*(t.x-this.lambda0),n=Math.atan(Math.sin(e)/(Math.sin(this.b0)*Math.tan(h)+Math.cos(this.b0)*Math.cos(e))),r=Math.asin(Math.cos(this.b0)*Math.sin(h)-Math.sin(this.b0)*Math.cos(h)*Math.cos(e));return t.y=this.R/2*Math.log((1+Math.sin(r))/(1-Math.sin(r)))+this.y0,t.x=this.R*n+this.x0,t},inverse:function(t){for(var s=t.x-this.x0,i=t.y-this.y0,a=s/this.R,h=2*(Math.atan(Math.exp(i/this.R))-Math.PI/4),e=Math.asin(Math.cos(this.b0)*Math.sin(h)+Math.sin(this.b0)*Math.cos(h)*Math.cos(a)),n=Math.atan(Math.sin(a)/(Math.cos(this.b0)*Math.cos(a)-Math.sin(this.b0)*Math.tan(h))),r=this.lambda0+n/this.alpha,o=0,l=e,u=-1e3,c=0;Math.abs(l-u)&gt;1e-7;){if(++c&gt;20)return;o=1/this.alpha*(Math.log(Math.tan(Math.PI/4+e/2))-this.K)+this.e*Math.log(Math.tan(Math.PI/4+Math.asin(this.e*Math.sin(l))/2)),u=l,l=2*Math.atan(Math.exp(o))-Math.PI/2}return t.x=r,t.y=l,t}}}),i(&quot;proj4/projCode/omerc&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.no_off=this.no_off||!1,this.no_rot=this.no_rot||!1,isNaN(this.k0)&amp;&amp;(this.k0=1);var s=Math.sin(this.lat0),i=Math.cos(this.lat0),a=this.e*s;this.bl=Math.sqrt(1+this.es/(1-this.es)*Math.pow(i,4)),this.al=this.a*this.bl*this.k0*Math.sqrt(1-this.es)/(1-a*a);var h=t.tsfnz(this.e,this.lat0,s),e=this.bl/i*Math.sqrt((1-this.es)/(1-a*a));1&gt;e*e&amp;&amp;(e=1);var n,r;if(isNaN(this.longc)){var o=t.tsfnz(this.e,this.lat1,Math.sin(this.lat1)),l=t.tsfnz(this.e,this.lat2,Math.sin(this.lat2));this.el=this.lat0&gt;=0?(e+Math.sqrt(e*e-1))*Math.pow(h,this.bl):(e-Math.sqrt(e*e-1))*Math.pow(h,this.bl);var u=Math.pow(o,this.bl),c=Math.pow(l,this.bl);n=this.el/u,r=.5*(n-1/n);var M=(this.el*this.el-c*u)/(this.el*this.el+c*u),f=(c-u)/(c+u),m=t.adjust_lon(this.long1-this.long2);this.long0=.5*(this.long1+this.long2)-Math.atan(M*Math.tan(.5*this.bl*m)/f)/this.bl,this.long0=t.adjust_lon(this.long0);var p=t.adjust_lon(this.long1-this.long0);this.gamma0=Math.atan(Math.sin(this.bl*p)/r),this.alpha=Math.asin(e*Math.sin(this.gamma0))}else n=this.lat0&gt;=0?e+Math.sqrt(e*e-1):e-Math.sqrt(e*e-1),this.el=n*Math.pow(h,this.bl),r=.5*(n-1/n),this.gamma0=Math.asin(Math.sin(this.alpha)/e),this.long0=this.longc-Math.asin(r*Math.tan(this.gamma0))/this.bl;this.uc=this.no_off?0:this.lat0&gt;=0?this.al/this.bl*Math.atan2(Math.sqrt(e*e-1),Math.cos(this.alpha)):-1*this.al/this.bl*Math.atan2(Math.sqrt(e*e-1),Math.cos(this.alpha))},forward:function(s){var i,a,h,e=s.x,n=s.y,r=t.adjust_lon(e-this.long0);if(Math.abs(Math.abs(n)-t.HALF_PI)&lt;=t.EPSLN)h=n&gt;0?-1:1,a=this.al/this.bl*Math.log(Math.tan(t.FORTPI+.5*h*this.gamma0)),i=-1*h*t.HALF_PI*this.al/this.bl;else{var o=t.tsfnz(this.e,n,Math.sin(n)),l=this.el/Math.pow(o,this.bl),u=.5*(l-1/l),c=.5*(l+1/l),M=Math.sin(this.bl*r),f=(u*Math.sin(this.gamma0)-M*Math.cos(this.gamma0))/c;a=Math.abs(Math.abs(f)-1)&lt;=t.EPSLN?Number.POSITIVE_INFINITY:.5*this.al*Math.log((1-f)/(1+f))/this.bl,i=Math.abs(Math.cos(this.bl*r))&lt;=t.EPSLN?this.al*this.bl*r:this.al*Math.atan2(u*Math.cos(this.gamma0)+M*Math.sin(this.gamma0),Math.cos(this.bl*r))/this.bl}return this.no_rot?(s.x=this.x0+i,s.y=this.y0+a):(i-=this.uc,s.x=this.x0+a*Math.cos(this.alpha)+i*Math.sin(this.alpha),s.y=this.y0+i*Math.cos(this.alpha)-a*Math.sin(this.alpha)),s},inverse:function(s){var i,a;this.no_rot?(a=s.y-this.y0,i=s.x-this.x0):(a=(s.x-this.x0)*Math.cos(this.alpha)-(s.y-this.y0)*Math.sin(this.alpha),i=(s.y-this.y0)*Math.cos(this.alpha)+(s.x-this.x0)*Math.sin(this.alpha),i+=this.uc);var h=Math.exp(-1*this.bl*a/this.al),e=.5*(h-1/h),n=.5*(h+1/h),r=Math.sin(this.bl*i/this.al),o=(r*Math.cos(this.gamma0)+e*Math.sin(this.gamma0))/n,l=Math.pow(this.el/Math.sqrt((1+o)/(1-o)),1/this.bl);return Math.abs(o-1)&lt;t.EPSLN?(s.x=this.long0,s.y=t.HALF_PI):Math.abs(o+1)&lt;t.EPSLN?(s.x=this.long0,s.y=-1*t.HALF_PI):(s.y=t.phi2z(this.e,l),s.x=t.adjust_lon(this.long0-Math.atan2(e*Math.cos(this.gamma0)-r*Math.sin(this.gamma0),Math.cos(this.bl*i/this.al))/this.bl)),s}}}),i(&quot;proj4/projCode/lcc&quot;,[&quot;../common&quot;],function(t){return{init:function(){if(this.lat2||(this.lat2=this.lat1),this.k0||(this.k0=1),!(Math.abs(this.lat1+this.lat2)&lt;t.EPSLN)){var s=this.b/this.a;this.e=Math.sqrt(1-s*s);var i=Math.sin(this.lat1),a=Math.cos(this.lat1),h=t.msfnz(this.e,i,a),e=t.tsfnz(this.e,this.lat1,i),n=Math.sin(this.lat2),r=Math.cos(this.lat2),o=t.msfnz(this.e,n,r),l=t.tsfnz(this.e,this.lat2,n),u=t.tsfnz(this.e,this.lat0,Math.sin(this.lat0));this.ns=Math.abs(this.lat1-this.lat2)&gt;t.EPSLN?Math.log(h/o)/Math.log(e/l):i,isNaN(this.ns)&amp;&amp;(this.ns=i),this.f0=h/(this.ns*Math.pow(e,this.ns)),this.rh=this.a*this.f0*Math.pow(u,this.ns),this.title||(this.title=&quot;Lambert Conformal Conic&quot;)}},forward:function(s){var i=s.x,a=s.y;Math.abs(2*Math.abs(a)-t.PI)&lt;=t.EPSLN&amp;&amp;(a=t.sign(a)*(t.HALF_PI-2*t.EPSLN));var h,e,n=Math.abs(Math.abs(a)-t.HALF_PI);if(n&gt;t.EPSLN)h=t.tsfnz(this.e,a,Math.sin(a)),e=this.a*this.f0*Math.pow(h,this.ns);else{if(n=a*this.ns,0&gt;=n)return null;e=0}var r=this.ns*t.adjust_lon(i-this.long0);return s.x=this.k0*e*Math.sin(r)+this.x0,s.y=this.k0*(this.rh-e*Math.cos(r))+this.y0,s},inverse:function(s){var i,a,h,e,n,r=(s.x-this.x0)/this.k0,o=this.rh-(s.y-this.y0)/this.k0;this.ns&gt;0?(i=Math.sqrt(r*r+o*o),a=1):(i=-Math.sqrt(r*r+o*o),a=-1);var l=0;if(0!==i&amp;&amp;(l=Math.atan2(a*r,a*o)),0!==i||this.ns&gt;0){if(a=1/this.ns,h=Math.pow(i/(this.a*this.f0),a),e=t.phi2z(this.e,h),-9999===e)return null}else e=-t.HALF_PI;return n=t.adjust_lon(l/this.ns+this.long0),s.x=n,s.y=e,s}}}),i(&quot;proj4/projCode/krovak&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.a=6377397.155,this.es=.006674372230614,this.e=Math.sqrt(this.es),this.lat0||(this.lat0=.863937979737193),this.long0||(this.long0=.4334234309119251),this.k0||(this.k0=.9999),this.s45=.785398163397448,this.s90=2*this.s45,this.fi0=this.lat0,this.e2=this.es,this.e=Math.sqrt(this.e2),this.alfa=Math.sqrt(1+this.e2*Math.pow(Math.cos(this.fi0),4)/(1-this.e2)),this.uq=1.04216856380474,this.u0=Math.asin(Math.sin(this.fi0)/this.alfa),this.g=Math.pow((1+this.e*Math.sin(this.fi0))/(1-this.e*Math.sin(this.fi0)),this.alfa*this.e/2),this.k=Math.tan(this.u0/2+this.s45)/Math.pow(Math.tan(this.fi0/2+this.s45),this.alfa)*this.g,this.k1=this.k0,this.n0=this.a*Math.sqrt(1-this.e2)/(1-this.e2*Math.pow(Math.sin(this.fi0),2)),this.s0=1.37008346281555,this.n=Math.sin(this.s0),this.ro0=this.k1*this.n0/Math.tan(this.s0),this.ad=this.s90-this.uq},forward:function(s){var i,a,h,e,n,r,o,l=s.x,u=s.y,c=t.adjust_lon(l-this.long0);return i=Math.pow((1+this.e*Math.sin(u))/(1-this.e*Math.sin(u)),this.alfa*this.e/2),a=2*(Math.atan(this.k*Math.pow(Math.tan(u/2+this.s45),this.alfa)/i)-this.s45),h=-c*this.alfa,e=Math.asin(Math.cos(this.ad)*Math.sin(a)+Math.sin(this.ad)*Math.cos(a)*Math.cos(h)),n=Math.asin(Math.cos(a)*Math.sin(h)/Math.cos(e)),r=this.n*n,o=this.ro0*Math.pow(Math.tan(this.s0/2+this.s45),this.n)/Math.pow(Math.tan(e/2+this.s45),this.n),s.y=o*Math.cos(r)/1,s.x=o*Math.sin(r)/1,this.czech||(s.y*=-1,s.x*=-1),s},inverse:function(t){var s,i,a,h,e,n,r,o,l=t.x;t.x=t.y,t.y=l,this.czech||(t.y*=-1,t.x*=-1),n=Math.sqrt(t.x*t.x+t.y*t.y),e=Math.atan2(t.y,t.x),h=e/Math.sin(this.s0),a=2*(Math.atan(Math.pow(this.ro0/n,1/this.n)*Math.tan(this.s0/2+this.s45))-this.s45),s=Math.asin(Math.cos(this.ad)*Math.sin(a)-Math.sin(this.ad)*Math.cos(a)*Math.cos(h)),i=Math.asin(Math.cos(a)*Math.sin(h)/Math.cos(s)),t.x=this.long0-i/this.alfa,r=s,o=0;var u=0;do t.y=2*(Math.atan(Math.pow(this.k,-1/this.alfa)*Math.pow(Math.tan(s/2+this.s45),1/this.alfa)*Math.pow((1+this.e*Math.sin(r))/(1-this.e*Math.sin(r)),this.e/2))-this.s45),Math.abs(r-t.y)&lt;1e-10&amp;&amp;(o=1),r=t.y,u+=1;while(0===o&amp;&amp;15&gt;u);return u&gt;=15?null:t}}}),i(&quot;proj4/projCode/cass&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.sphere||(this.e0=t.e0fn(this.es),this.e1=t.e1fn(this.es),this.e2=t.e2fn(this.es),this.e3=t.e3fn(this.es),this.ml0=this.a*t.mlfn(this.e0,this.e1,this.e2,this.e3,this.lat0))},forward:function(s){var i,a,h=s.x,e=s.y;if(h=t.adjust_lon(h-this.long0),this.sphere)i=this.a*Math.asin(Math.cos(e)*Math.sin(h)),a=this.a*(Math.atan2(Math.tan(e),Math.cos(h))-this.lat0);else{var n=Math.sin(e),r=Math.cos(e),o=t.gN(this.a,this.e,n),l=Math.tan(e)*Math.tan(e),u=h*Math.cos(e),c=u*u,M=this.es*r*r/(1-this.es),f=this.a*t.mlfn(this.e0,this.e1,this.e2,this.e3,e);i=o*u*(1-c*l*(1/6-(8-l+8*M)*c/120)),a=f-this.ml0+o*n/r*c*(.5+(5-l+6*M)*c/24)}return s.x=i+this.x0,s.y=a+this.y0,s},inverse:function(s){s.x-=this.x0,s.y-=this.y0;var i,a,h=s.x/this.a,e=s.y/this.a;if(this.sphere){var n=e+this.lat0;i=Math.asin(Math.sin(n)*Math.cos(h)),a=Math.atan2(Math.tan(h),Math.cos(n))}else{var r=this.ml0/this.a+e,o=t.imlfn(r,this.e0,this.e1,this.e2,this.e3);if(Math.abs(Math.abs(o)-t.HALF_PI)&lt;=t.EPSLN)return s.x=this.long0,s.y=t.HALF_PI,0&gt;e&amp;&amp;(s.y*=-1),s;var l=t.gN(this.a,this.e,Math.sin(o)),u=l*l*l/this.a/this.a*(1-this.es),c=Math.pow(Math.tan(o),2),M=h*this.a/l,f=M*M;i=o-l*Math.tan(o)/u*M*M*(.5-(1+3*c)*M*M/24),a=M*(1-f*(c/3+(1+3*c)*c*f/15))/Math.cos(o)}return s.x=t.adjust_lon(a+this.long0),s.y=t.adjust_lat(i),s}}}),i(&quot;proj4/projCode/laea&quot;,[&quot;../common&quot;],function(t){return{S_POLE:1,N_POLE:2,EQUIT:3,OBLIQ:4,init:function(){var s=Math.abs(this.lat0);if(this.mode=Math.abs(s-t.HALF_PI)&lt;t.EPSLN?this.lat0&lt;0?this.S_POLE:this.N_POLE:Math.abs(s)&lt;t.EPSLN?this.EQUIT:this.OBLIQ,this.es&gt;0){var i;switch(this.qp=t.qsfnz(this.e,1),this.mmf=.5/(1-this.es),this.apa=this.authset(this.es),this.mode){case this.N_POLE:this.dd=1;break;case this.S_POLE:this.dd=1;break;case this.EQUIT:this.rq=Math.sqrt(.5*this.qp),this.dd=1/this.rq,this.xmf=1,this.ymf=.5*this.qp;break;case this.OBLIQ:this.rq=Math.sqrt(.5*this.qp),i=Math.sin(this.lat0),this.sinb1=t.qsfnz(this.e,i)/this.qp,this.cosb1=Math.sqrt(1-this.sinb1*this.sinb1),this.dd=Math.cos(this.lat0)/(Math.sqrt(1-this.es*i*i)*this.rq*this.cosb1),this.ymf=(this.xmf=this.rq)/this.dd,this.xmf*=this.dd}}else this.mode===this.OBLIQ&amp;&amp;(this.sinph0=Math.sin(this.lat0),this.cosph0=Math.cos(this.lat0))},forward:function(s){var i,a,h,e,n,r,o,l,u,c,M=s.x,f=s.y;if(M=t.adjust_lon(M-this.long0),this.sphere){if(n=Math.sin(f),c=Math.cos(f),h=Math.cos(M),this.mode===this.OBLIQ||this.mode===this.EQUIT){if(a=this.mode===this.EQUIT?1+c*h:1+this.sinph0*n+this.cosph0*c*h,a&lt;=t.EPSLN)return null;a=Math.sqrt(2/a),i=a*c*Math.sin(M),a*=this.mode===this.EQUIT?n:this.cosph0*n-this.sinph0*c*h}else if(this.mode===this.N_POLE||this.mode===this.S_POLE){if(this.mode===this.N_POLE&amp;&amp;(h=-h),Math.abs(f+this.phi0)&lt;t.EPSLN)return null;a=t.FORTPI-.5*f,a=2*(this.mode===this.S_POLE?Math.cos(a):Math.sin(a)),i=a*Math.sin(M),a*=h}}else{switch(o=0,l=0,u=0,h=Math.cos(M),e=Math.sin(M),n=Math.sin(f),r=t.qsfnz(this.e,n),(this.mode===this.OBLIQ||this.mode===this.EQUIT)&amp;&amp;(o=r/this.qp,l=Math.sqrt(1-o*o)),this.mode){case this.OBLIQ:u=1+this.sinb1*o+this.cosb1*l*h;break;case this.EQUIT:u=1+l*h;break;case this.N_POLE:u=t.HALF_PI+f,r=this.qp-r;break;case this.S_POLE:u=f-t.HALF_PI,r=this.qp+r}if(Math.abs(u)&lt;t.EPSLN)return null;switch(this.mode){case this.OBLIQ:case this.EQUIT:u=Math.sqrt(2/u),a=this.mode===this.OBLIQ?this.ymf*u*(this.cosb1*o-this.sinb1*l*h):(u=Math.sqrt(2/(1+l*h)))*o*this.ymf,i=this.xmf*u*l*e;break;case this.N_POLE:case this.S_POLE:r&gt;=0?(i=(u=Math.sqrt(r))*e,a=h*(this.mode===this.S_POLE?u:-u)):i=a=0}}return s.x=this.a*i+this.x0,s.y=this.a*a+this.y0,s},inverse:function(s){s.x-=this.x0,s.y-=this.y0;var i,a,h,e,n,r,o,l=s.x/this.a,u=s.y/this.a;if(this.sphere){var c,M=0,f=0;if(c=Math.sqrt(l*l+u*u),a=.5*c,a&gt;1)return null;switch(a=2*Math.asin(a),(this.mode===this.OBLIQ||this.mode===this.EQUIT)&amp;&amp;(f=Math.sin(a),M=Math.cos(a)),this.mode){case this.EQUIT:a=Math.abs(c)&lt;=t.EPSLN?0:Math.asin(u*f/c),l*=f,u=M*c;break;case this.OBLIQ:a=Math.abs(c)&lt;=t.EPSLN?this.phi0:Math.asin(M*this.sinph0+u*f*this.cosph0/c),l*=f*this.cosph0,u=(M-Math.sin(a)*this.sinph0)*c;break;case this.N_POLE:u=-u,a=t.HALF_PI-a;break;case this.S_POLE:a-=t.HALF_PI}i=0!==u||this.mode!==this.EQUIT&amp;&amp;this.mode!==this.OBLIQ?Math.atan2(l,u):0}else{if(o=0,this.mode===this.OBLIQ||this.mode===this.EQUIT){if(l/=this.dd,u*=this.dd,r=Math.sqrt(l*l+u*u),r&lt;t.EPSLN)return s.x=0,s.y=this.phi0,s;e=2*Math.asin(.5*r/this.rq),h=Math.cos(e),l*=e=Math.sin(e),this.mode===this.OBLIQ?(o=h*this.sinb1+u*e*this.cosb1/r,n=this.qp*o,u=r*this.cosb1*h-u*this.sinb1*e):(o=u*e/r,n=this.qp*o,u=r*h)}else if(this.mode===this.N_POLE||this.mode===this.S_POLE){if(this.mode===this.N_POLE&amp;&amp;(u=-u),n=l*l+u*u,!n)return s.x=0,s.y=this.phi0,s;o=1-n/this.qp,this.mode===this.S_POLE&amp;&amp;(o=-o)}i=Math.atan2(l,u),a=this.authlat(Math.asin(o),this.apa)}return s.x=t.adjust_lon(this.long0+i),s.y=a,s},P00:.3333333333333333,P01:.17222222222222222,P02:.10257936507936508,P10:.06388888888888888,P11:.0664021164021164,P20:.016415012942191543,authset:function(t){var s,i=[];return i[0]=t*this.P00,s=t*t,i[0]+=s*this.P01,i[1]=s*this.P10,s*=t,i[0]+=s*this.P02,i[1]+=s*this.P11,i[2]=s*this.P20,i},authlat:function(t,s){var i=t+t;return t+s[0]*Math.sin(i)+s[1]*Math.sin(i+i)+s[2]*Math.sin(i+i+i)}}}),i(&quot;proj4/projCode/merc&quot;,[&quot;../common&quot;],function(t){return{init:function(){var s=this.b/this.a;this.es=1-s*s,this.e=Math.sqrt(this.es),this.lat_ts?this.k0=this.sphere?Math.cos(this.lat_ts):t.msfnz(this.e,Math.sin(this.lat_ts),Math.cos(this.lat_ts)):this.k0||(this.k0=this.k?this.k:1)},forward:function(s){var i=s.x,a=s.y;if(a*t.R2D&gt;90&amp;&amp;a*t.R2D&lt;-90&amp;&amp;i*t.R2D&gt;180&amp;&amp;i*t.R2D&lt;-180)return null;var h,e;if(Math.abs(Math.abs(a)-t.HALF_PI)&lt;=t.EPSLN)return null;if(this.sphere)h=this.x0+this.a*this.k0*t.adjust_lon(i-this.long0),e=this.y0+this.a*this.k0*Math.log(Math.tan(t.FORTPI+.5*a));else{var n=Math.sin(a),r=t.tsfnz(this.e,a,n);h=this.x0+this.a*this.k0*t.adjust_lon(i-this.long0),e=this.y0-this.a*this.k0*Math.log(r)}return s.x=h,s.y=e,s},inverse:function(s){var i,a,h=s.x-this.x0,e=s.y-this.y0;if(this.sphere)a=t.HALF_PI-2*Math.atan(Math.exp(-e/(this.a*this.k0)));else{var n=Math.exp(-e/(this.a*this.k0));if(a=t.phi2z(this.e,n),-9999===a)return null}return i=t.adjust_lon(this.long0+h/(this.a*this.k0)),s.x=i,s.y=a,s}}}),i(&quot;proj4/projCode/aea&quot;,[&quot;../common&quot;],function(t){return{init:function(){Math.abs(this.lat1+this.lat2)&lt;t.EPSLN||(this.temp=this.b/this.a,this.es=1-Math.pow(this.temp,2),this.e3=Math.sqrt(this.es),this.sin_po=Math.sin(this.lat1),this.cos_po=Math.cos(this.lat1),this.t1=this.sin_po,this.con=this.sin_po,this.ms1=t.msfnz(this.e3,this.sin_po,this.cos_po),this.qs1=t.qsfnz(this.e3,this.sin_po,this.cos_po),this.sin_po=Math.sin(this.lat2),this.cos_po=Math.cos(this.lat2),this.t2=this.sin_po,this.ms2=t.msfnz(this.e3,this.sin_po,this.cos_po),this.qs2=t.qsfnz(this.e3,this.sin_po,this.cos_po),this.sin_po=Math.sin(this.lat0),this.cos_po=Math.cos(this.lat0),this.t3=this.sin_po,this.qs0=t.qsfnz(this.e3,this.sin_po,this.cos_po),this.ns0=Math.abs(this.lat1-this.lat2)&gt;t.EPSLN?(this.ms1*this.ms1-this.ms2*this.ms2)/(this.qs2-this.qs1):this.con,this.c=this.ms1*this.ms1+this.ns0*this.qs1,this.rh=this.a*Math.sqrt(this.c-this.ns0*this.qs0)/this.ns0)},forward:function(s){var i=s.x,a=s.y;this.sin_phi=Math.sin(a),this.cos_phi=Math.cos(a);var h=t.qsfnz(this.e3,this.sin_phi,this.cos_phi),e=this.a*Math.sqrt(this.c-this.ns0*h)/this.ns0,n=this.ns0*t.adjust_lon(i-this.long0),r=e*Math.sin(n)+this.x0,o=this.rh-e*Math.cos(n)+this.y0;return s.x=r,s.y=o,s},inverse:function(s){var i,a,h,e,n,r;return s.x-=this.x0,s.y=this.rh-s.y+this.y0,this.ns0&gt;=0?(i=Math.sqrt(s.x*s.x+s.y*s.y),h=1):(i=-Math.sqrt(s.x*s.x+s.y*s.y),h=-1),e=0,0!==i&amp;&amp;(e=Math.atan2(h*s.x,h*s.y)),h=i*this.ns0/this.a,this.sphere?r=Math.asin((this.c-h*h)/(2*this.ns0)):(a=(this.c-h*h)/this.ns0,r=this.phi1z(this.e3,a)),n=t.adjust_lon(e/this.ns0+this.long0),s.x=n,s.y=r,s},phi1z:function(s,i){var a,h,e,n,r,o=t.asinz(.5*i);if(s&lt;t.EPSLN)return o;for(var l=s*s,u=1;25&gt;=u;u++)if(a=Math.sin(o),h=Math.cos(o),e=s*a,n=1-e*e,r=.5*n*n/h*(i/(1-l)-a/n+.5/s*Math.log((1-e)/(1+e))),o+=r,Math.abs(r)&lt;=1e-7)return o;return null}}}),i(&quot;proj4/projCode/gnom&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.sin_p14=Math.sin(this.lat0),this.cos_p14=Math.cos(this.lat0),this.infinity_dist=1e3*this.a,this.rc=1},forward:function(s){var i,a,h,e,n,r,o,l,u=s.x,c=s.y;return h=t.adjust_lon(u-this.long0),i=Math.sin(c),a=Math.cos(c),e=Math.cos(h),r=this.sin_p14*i+this.cos_p14*a*e,n=1,r&gt;0||Math.abs(r)&lt;=t.EPSLN?(o=this.x0+this.a*n*a*Math.sin(h)/r,l=this.y0+this.a*n*(this.cos_p14*i-this.sin_p14*a*e)/r):(o=this.x0+this.infinity_dist*a*Math.sin(h),l=this.y0+this.infinity_dist*(this.cos_p14*i-this.sin_p14*a*e)),s.x=o,s.y=l,s},inverse:function(s){var i,a,h,e,n,r;return s.x=(s.x-this.x0)/this.a,s.y=(s.y-this.y0)/this.a,s.x/=this.k0,s.y/=this.k0,(i=Math.sqrt(s.x*s.x+s.y*s.y))?(e=Math.atan2(i,this.rc),a=Math.sin(e),h=Math.cos(e),r=t.asinz(h*this.sin_p14+s.y*a*this.cos_p14/i),n=Math.atan2(s.x*a,i*this.cos_p14*h-s.y*this.sin_p14*a),n=t.adjust_lon(this.long0+n)):(r=this.phic0,n=0),s.x=n,s.y=r,s}}}),i(&quot;proj4/projCode/cea&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.sphere||(this.k0=t.msfnz(this.e,Math.sin(this.lat_ts),Math.cos(this.lat_ts)))},forward:function(s){var i,a,h=s.x,e=s.y,n=t.adjust_lon(h-this.long0);if(this.sphere)i=this.x0+this.a*n*Math.cos(this.lat_ts),a=this.y0+this.a*Math.sin(e)/Math.cos(this.lat_ts);else{var r=t.qsfnz(this.e,Math.sin(e));i=this.x0+this.a*this.k0*n,a=this.y0+.5*this.a*r/this.k0}return s.x=i,s.y=a,s},inverse:function(s){s.x-=this.x0,s.y-=this.y0;var i,a;return this.sphere?(i=t.adjust_lon(this.long0+s.x/this.a/Math.cos(this.lat_ts)),a=Math.asin(s.y/this.a*Math.cos(this.lat_ts))):(a=t.iqsfnz(this.e,2*s.y*this.k0/this.a),i=t.adjust_lon(this.long0+s.x/(this.a*this.k0))),s.x=i,s.y=a,s}}}),i(&quot;proj4/projCode/eqc&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.x0=this.x0||0,this.y0=this.y0||0,this.lat0=this.lat0||0,this.long0=this.long0||0,this.lat_ts=this.lat_t||0,this.title=this.title||&quot;Equidistant Cylindrical (Plate Carre)&quot;,this.rc=Math.cos(this.lat_ts)},forward:function(s){var i=s.x,a=s.y,h=t.adjust_lon(i-this.long0),e=t.adjust_lat(a-this.lat0);return s.x=this.x0+this.a*h*this.rc,s.y=this.y0+this.a*e,s},inverse:function(s){var i=s.x,a=s.y;return s.x=t.adjust_lon(this.long0+(i-this.x0)/(this.a*this.rc)),s.y=t.adjust_lat(this.lat0+(a-this.y0)/this.a),s}}}),i(&quot;proj4/projCode/poly&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.temp=this.b/this.a,this.es=1-Math.pow(this.temp,2),this.e=Math.sqrt(this.es),this.e0=t.e0fn(this.es),this.e1=t.e1fn(this.es),this.e2=t.e2fn(this.es),this.e3=t.e3fn(this.es),this.ml0=this.a*t.mlfn(this.e0,this.e1,this.e2,this.e3,this.lat0)},forward:function(s){var i,a,h,e=s.x,n=s.y,r=t.adjust_lon(e-this.long0);if(h=r*Math.sin(n),this.sphere)Math.abs(n)&lt;=t.EPSLN?(i=this.a*r,a=-1*this.a*this.lat0):(i=this.a*Math.sin(h)/Math.tan(n),a=this.a*(t.adjust_lat(n-this.lat0)+(1-Math.cos(h))/Math.tan(n)));else if(Math.abs(n)&lt;=t.EPSLN)i=this.a*r,a=-1*this.ml0;else{var o=t.gN(this.a,this.e,Math.sin(n))/Math.tan(n);i=o*Math.sin(h),a=this.a*t.mlfn(this.e0,this.e1,this.e2,this.e3,n)-this.ml0+o*(1-Math.cos(h))}return s.x=i+this.x0,s.y=a+this.y0,s},inverse:function(s){var i,a,h,e,n,r,o,l,u;if(h=s.x-this.x0,e=s.y-this.y0,this.sphere)if(Math.abs(e+this.a*this.lat0)&lt;=t.EPSLN)i=t.adjust_lon(h/this.a+this.long0),a=0;else{r=this.lat0+e/this.a,o=h*h/this.a/this.a+r*r,l=r;var c;for(n=t.MAX_ITER;n;--n)if(c=Math.tan(l),u=-1*(r*(l*c+1)-l-.5*(l*l+o)*c)/((l-r)/c-1),l+=u,Math.abs(u)&lt;=t.EPSLN){a=l;break}i=t.adjust_lon(this.long0+Math.asin(h*Math.tan(l)/this.a)/Math.sin(a))}else if(Math.abs(e+this.ml0)&lt;=t.EPSLN)a=0,i=t.adjust_lon(this.long0+h/this.a);else{r=(this.ml0+e)/this.a,o=h*h/this.a/this.a+r*r,l=r;var M,f,m,p,_;for(n=t.MAX_ITER;n;--n)if(_=this.e*Math.sin(l),M=Math.sqrt(1-_*_)*Math.tan(l),f=this.a*t.mlfn(this.e0,this.e1,this.e2,this.e3,l),m=this.e0-2*this.e1*Math.cos(2*l)+4*this.e2*Math.cos(4*l)-6*this.e3*Math.cos(6*l),p=f/this.a,u=(r*(M*p+1)-p-.5*M*(p*p+o))/(this.es*Math.sin(2*l)*(p*p+o-2*r*p)/(4*M)+(r-p)*(M*m-2/Math.sin(2*l))-m),l-=u,Math.abs(u)&lt;=t.EPSLN){a=l;break}M=Math.sqrt(1-this.es*Math.pow(Math.sin(a),2))*Math.tan(a),i=t.adjust_lon(this.long0+Math.asin(h*M/this.a)/Math.sin(a))}return s.x=i,s.y=a,s}}}),i(&quot;proj4/projCode/nzmg&quot;,[&quot;../common&quot;],function(t){return{iterations:1,init:function(){this.A=[],this.A[1]=.6399175073,this.A[2]=-.1358797613,this.A[3]=.063294409,this.A[4]=-.02526853,this.A[5]=.0117879,this.A[6]=-.0055161,this.A[7]=.0026906,this.A[8]=-.001333,this.A[9]=67e-5,this.A[10]=-34e-5,this.B_re=[],this.B_im=[],this.B_re[1]=.7557853228,this.B_im[1]=0,this.B_re[2]=.249204646,this.B_im[2]=.003371507,this.B_re[3]=-.001541739,this.B_im[3]=.04105856,this.B_re[4]=-.10162907,this.B_im[4]=.01727609,this.B_re[5]=-.26623489,this.B_im[5]=-.36249218,this.B_re[6]=-.6870983,this.B_im[6]=-1.1651967,this.C_re=[],this.C_im=[],this.C_re[1]=1.3231270439,this.C_im[1]=0,this.C_re[2]=-.577245789,this.C_im[2]=-.007809598,this.C_re[3]=.508307513,this.C_im[3]=-.112208952,this.C_re[4]=-.15094762,this.C_im[4]=.18200602,this.C_re[5]=1.01418179,this.C_im[5]=1.64497696,this.C_re[6]=1.9660549,this.C_im[6]=2.5127645,this.D=[],this.D[1]=1.5627014243,this.D[2]=.5185406398,this.D[3]=-.03333098,this.D[4]=-.1052906,this.D[5]=-.0368594,this.D[6]=.007317,this.D[7]=.0122,this.D[8]=.00394,this.D[9]=-.0013},forward:function(s){var i,a=s.x,h=s.y,e=h-this.lat0,n=a-this.long0,r=1e-5*(e/t.SEC_TO_RAD),o=n,l=1,u=0;for(i=1;10&gt;=i;i++)l*=r,u+=this.A[i]*l;var c,M,f=u,m=o,p=1,_=0,d=0,y=0;for(i=1;6&gt;=i;i++)c=p*f-_*m,M=_*f+p*m,p=c,_=M,d=d+this.B_re[i]*p-this.B_im[i]*_,y=y+this.B_im[i]*p+this.B_re[i]*_;return s.x=y*this.a+this.x0,s.y=d*this.a+this.y0,s},inverse:function(s){var i,a,h,e=s.x,n=s.y,r=e-this.x0,o=n-this.y0,l=o/this.a,u=r/this.a,c=1,M=0,f=0,m=0;for(i=1;6&gt;=i;i++)a=c*l-M*u,h=M*l+c*u,c=a,M=h,f=f+this.C_re[i]*c-this.C_im[i]*M,m=m+this.C_im[i]*c+this.C_re[i]*M;for(var p=0;p&lt;this.iterations;p++){var _,d,y=f,g=m,x=l,v=u;for(i=2;6&gt;=i;i++)_=y*f-g*m,d=g*f+y*m,y=_,g=d,x+=(i-1)*(this.B_re[i]*y-this.B_im[i]*g),v+=(i-1)*(this.B_im[i]*y+this.B_re[i]*g);y=1,g=0;var P=this.B_re[1],b=this.B_im[1];for(i=2;6&gt;=i;i++)_=y*f-g*m,d=g*f+y*m,y=_,g=d,P+=i*(this.B_re[i]*y-this.B_im[i]*g),b+=i*(this.B_im[i]*y+this.B_re[i]*g);var C=P*P+b*b;f=(x*P+v*b)/C,m=(v*P-x*b)/C}var S=f,j=m,N=1,A=0;for(i=1;9&gt;=i;i++)N*=S,A+=this.D[i]*N;var I=this.lat0+1e5*A*t.SEC_TO_RAD,E=this.long0+j;return s.x=E,s.y=I,s}}}),i(&quot;proj4/projCode/mill&quot;,[&quot;../common&quot;],function(t){return{init:function(){},forward:function(s){var i=s.x,a=s.y,h=t.adjust_lon(i-this.long0),e=this.x0+this.a*h,n=this.y0+1.25*this.a*Math.log(Math.tan(t.PI/4+a/2.5));return s.x=e,s.y=n,s},inverse:function(s){s.x-=this.x0,s.y-=this.y0;var i=t.adjust_lon(this.long0+s.x/this.a),a=2.5*(Math.atan(Math.exp(.8*s.y/this.a))-t.PI/4);return s.x=i,s.y=a,s}}}),i(&quot;proj4/projCode/sinu&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.sphere?(this.n=1,this.m=0,this.es=0,this.C_y=Math.sqrt((this.m+1)/this.n),this.C_x=this.C_y/(this.m+1)):this.en=t.pj_enfn(this.es)},forward:function(s){var i,a,h=s.x,e=s.y;if(h=t.adjust_lon(h-this.long0),this.sphere){if(this.m)for(var n=this.n*Math.sin(e),r=t.MAX_ITER;r;--r){var o=(this.m*e+Math.sin(e)-n)/(this.m+Math.cos(e));if(e-=o,Math.abs(o)&lt;t.EPSLN)break}else e=1!==this.n?Math.asin(this.n*Math.sin(e)):e;i=this.a*this.C_x*h*(this.m+Math.cos(e)),a=this.a*this.C_y*e}else{var l=Math.sin(e),u=Math.cos(e);a=this.a*t.pj_mlfn(e,l,u,this.en),i=this.a*h*u/Math.sqrt(1-this.es*l*l)}return s.x=i,s.y=a,s},inverse:function(s){var i,a,h;if(s.x-=this.x0,s.y-=this.y0,i=s.y/this.a,this.sphere)s.y/=this.C_y,i=this.m?Math.asin((this.m*s.y+Math.sin(s.y))/this.n):1!==this.n?Math.asin(Math.sin(s.y)/this.n):s.y,h=s.x/(this.C_x*(this.m+Math.cos(s.y)));else{i=t.pj_inv_mlfn(s.y/this.a,this.es,this.en);var e=Math.abs(i);e&lt;t.HALF_PI?(e=Math.sin(i),a=this.long0+s.x*Math.sqrt(1-this.es*e*e)/(this.a*Math.cos(i)),h=t.adjust_lon(a)):e-t.EPSLN&lt;t.HALF_PI&amp;&amp;(h=this.long0)}return s.x=h,s.y=i,s}}}),i(&quot;proj4/projCode/moll&quot;,[&quot;../common&quot;],function(t){return{init:function(){},forward:function(s){for(var i=s.x,a=s.y,h=t.adjust_lon(i-this.long0),e=a,n=t.PI*Math.sin(a),r=0;!0;r++){var o=-(e+Math.sin(e)-n)/(1+Math.cos(e));if(e+=o,Math.abs(o)&lt;t.EPSLN)break}e/=2,t.PI/2-Math.abs(a)&lt;t.EPSLN&amp;&amp;(h=0);var l=.900316316158*this.a*h*Math.cos(e)+this.x0,u=1.4142135623731*this.a*Math.sin(e)+this.y0;return s.x=l,s.y=u,s},inverse:function(s){var i,a;s.x-=this.x0,s.y-=this.y0,a=s.y/(1.4142135623731*this.a),Math.abs(a)&gt;.999999999999&amp;&amp;(a=.999999999999),i=Math.asin(a);var h=t.adjust_lon(this.long0+s.x/(.900316316158*this.a*Math.cos(i)));h&lt;-t.PI&amp;&amp;(h=-t.PI),h&gt;t.PI&amp;&amp;(h=t.PI),a=(2*i+Math.sin(2*i))/t.PI,Math.abs(a)&gt;1&amp;&amp;(a=1);var e=Math.asin(a);return s.x=h,s.y=e,s}}}),i(&quot;proj4/projCode/eqdc&quot;,[&quot;../common&quot;],function(t){return{init:function(){return Math.abs(this.lat1+this.lat2)&lt;t.EPSLN?(t.reportError(&quot;eqdc:init: Equal Latitudes&quot;),void 0):(this.lat2=this.lat2||this.lat1,this.temp=this.b/this.a,this.es=1-Math.pow(this.temp,2),this.e=Math.sqrt(this.es),this.e0=t.e0fn(this.es),this.e1=t.e1fn(this.es),this.e2=t.e2fn(this.es),this.e3=t.e3fn(this.es),this.sinphi=Math.sin(this.lat1),this.cosphi=Math.cos(this.lat1),this.ms1=t.msfnz(this.e,this.sinphi,this.cosphi),this.ml1=t.mlfn(this.e0,this.e1,this.e2,this.e3,this.lat1),Math.abs(this.lat1-this.lat2)&lt;t.EPSLN?this.ns=this.sinphi:(this.sinphi=Math.sin(this.lat2),this.cosphi=Math.cos(this.lat2),this.ms2=t.msfnz(this.e,this.sinphi,this.cosphi),this.ml2=t.mlfn(this.e0,this.e1,this.e2,this.e3,this.lat2),this.ns=(this.ms1-this.ms2)/(this.ml2-this.ml1)),this.g=this.ml1+this.ms1/this.ns,this.ml0=t.mlfn(this.e0,this.e1,this.e2,this.e3,this.lat0),this.rh=this.a*(this.g-this.ml0),void 0)},forward:function(s){var i,a=s.x,h=s.y;if(this.sphere)i=this.a*(this.g-h);else{var e=t.mlfn(this.e0,this.e1,this.e2,this.e3,h);i=this.a*(this.g-e)}var n=this.ns*t.adjust_lon(a-this.long0),r=this.x0+i*Math.sin(n),o=this.y0+this.rh-i*Math.cos(n);return s.x=r,s.y=o,s},inverse:function(s){s.x-=this.x0,s.y=this.rh-s.y+this.y0;var i,a,h,e;this.ns&gt;=0?(a=Math.sqrt(s.x*s.x+s.y*s.y),i=1):(a=-Math.sqrt(s.x*s.x+s.y*s.y),i=-1);var n=0;if(0!==a&amp;&amp;(n=Math.atan2(i*s.x,i*s.y)),this.sphere)return e=t.adjust_lon(this.long0+n/this.ns),h=t.adjust_lat(this.g-a/this.a),s.x=e,s.y=h,s;var r=this.g-a/this.a;return h=t.imlfn(r,this.e0,this.e1,this.e2,this.e3),e=t.adjust_lon(this.long0+n/this.ns),s.x=e,s.y=h,s}}}),i(&quot;proj4/projCode/vandg&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.R=this.a},forward:function(s){var i,a,h=s.x,e=s.y,n=t.adjust_lon(h-this.long0);Math.abs(e)&lt;=t.EPSLN&amp;&amp;(i=this.x0+this.R*n,a=this.y0);var r=t.asinz(2*Math.abs(e/t.PI));(Math.abs(n)&lt;=t.EPSLN||Math.abs(Math.abs(e)-t.HALF_PI)&lt;=t.EPSLN)&amp;&amp;(i=this.x0,a=e&gt;=0?this.y0+t.PI*this.R*Math.tan(.5*r):this.y0+t.PI*this.R*-Math.tan(.5*r));var o=.5*Math.abs(t.PI/n-n/t.PI),l=o*o,u=Math.sin(r),c=Math.cos(r),M=c/(u+c-1),f=M*M,m=M*(2/u-1),p=m*m,_=t.PI*this.R*(o*(M-p)+Math.sqrt(l*(M-p)*(M-p)-(p+l)*(f-p)))/(p+l);0&gt;n&amp;&amp;(_=-_),i=this.x0+_;var d=l+M;return _=t.PI*this.R*(m*d-o*Math.sqrt((p+l)*(l+1)-d*d))/(p+l),a=e&gt;=0?this.y0+_:this.y0-_,s.x=i,s.y=a,s},inverse:function(s){var i,a,h,e,n,r,o,l,u,c,M,f,m;return s.x-=this.x0,s.y-=this.y0,M=t.PI*this.R,h=s.x/M,e=s.y/M,n=h*h+e*e,r=-Math.abs(e)*(1+n),o=r-2*e*e+h*h,l=-2*r+1+2*e*e+n*n,m=e*e/l+(2*o*o*o/l/l/l-9*r*o/l/l)/27,u=(r-o*o/3/l)/l,c=2*Math.sqrt(-u/3),M=3*m/u/c,Math.abs(M)&gt;1&amp;&amp;(M=M&gt;=0?1:-1),f=Math.acos(M)/3,a=s.y&gt;=0?(-c*Math.cos(f+t.PI/3)-o/3/l)*t.PI:-(-c*Math.cos(f+t.PI/3)-o/3/l)*t.PI,i=Math.abs(h)&lt;t.EPSLN?this.long0:t.adjust_lon(this.long0+t.PI*(n-1+Math.sqrt(1+2*(h*h-e*e)+n*n))/2/h),s.x=i,s.y=a,s}}}),i(&quot;proj4/projCode/aeqd&quot;,[&quot;../common&quot;],function(t){return{init:function(){this.sin_p12=Math.sin(this.lat0),this.cos_p12=Math.cos(this.lat0)},forward:function(s){var i,a,h,e,n,r,o,l,u,c,M,f,m,p,_,d,y,g,x,v,P,b,C,S=s.x,j=s.y,N=Math.sin(s.y),A=Math.cos(s.y),I=t.adjust_lon(S-this.long0);return this.sphere?Math.abs(this.sin_p12-1)&lt;=t.EPSLN?(s.x=this.x0+this.a*(t.HALF_PI-j)*Math.sin(I),s.y=this.y0-this.a*(t.HALF_PI-j)*Math.cos(I),s):Math.abs(this.sin_p12+1)&lt;=t.EPSLN?(s.x=this.x0+this.a*(t.HALF_PI+j)*Math.sin(I),s.y=this.y0+this.a*(t.HALF_PI+j)*Math.cos(I),s):(g=this.sin_p12*N+this.cos_p12*A*Math.cos(I),d=Math.acos(g),y=d/Math.sin(d),s.x=this.x0+this.a*y*A*Math.sin(I),s.y=this.y0+this.a*y*(this.cos_p12*N-this.sin_p12*A*Math.cos(I)),s):(i=t.e0fn(this.es),a=t.e1fn(this.es),h=t.e2fn(this.es),e=t.e3fn(this.es),Math.abs(this.sin_p12-1)&lt;=t.EPSLN?(n=this.a*t.mlfn(i,a,h,e,t.HALF_PI),r=this.a*t.mlfn(i,a,h,e,j),s.x=this.x0+(n-r)*Math.sin(I),s.y=this.y0-(n-r)*Math.cos(I),s):Math.abs(this.sin_p12+1)&lt;=t.EPSLN?(n=this.a*t.mlfn(i,a,h,e,t.HALF_PI),r=this.a*t.mlfn(i,a,h,e,j),s.x=this.x0+(n+r)*Math.sin(I),s.y=this.y0+(n+r)*Math.cos(I),s):(o=N/A,l=t.gN(this.a,this.e,this.sin_p12),u=t.gN(this.a,this.e,N),c=Math.atan((1-this.es)*o+this.es*l*this.sin_p12/(u*A)),M=Math.atan2(Math.sin(I),this.cos_p12*Math.tan(c)-this.sin_p12*Math.cos(I)),x=0===M?Math.asin(this.cos_p12*Math.sin(c)-this.sin_p12*Math.cos(c)):Math.abs(Math.abs(M)-t.PI)&lt;=t.EPSLN?-Math.asin(this.cos_p12*Math.sin(c)-this.sin_p12*Math.cos(c)):Math.asin(Math.sin(I)*Math.cos(c)/Math.sin(M)),f=this.e*this.sin_p12/Math.sqrt(1-this.es),m=this.e*this.cos_p12*Math.cos(M)/Math.sqrt(1-this.es),p=f*m,_=m*m,v=x*x,P=v*x,b=P*x,C=b*x,d=l*x*(1-v*_*(1-_)/6+P/8*p*(1-2*_)+b/120*(_*(4-7*_)-3*f*f*(1-7*_))-C/48*p),s.x=this.x0+d*Math.sin(M),s.y=this.y0+d*Math.cos(M),s))},inverse:function(s){s.x-=this.x0,s.y-=this.y0;var i,a,h,e,n,r,o,l,u,c,M,f,m,p,_,d,y,g,x,v,P,b,C;if(this.sphere){if(i=Math.sqrt(s.x*s.x+s.y*s.y),i&gt;2*t.HALF_PI*this.a)return;return a=i/this.a,h=Math.sin(a),e=Math.cos(a),n=this.long0,Math.abs(i)&lt;=t.EPSLN?r=this.lat0:(r=t.asinz(e*this.sin_p12+s.y*h*this.cos_p12/i),o=Math.abs(this.lat0)-t.HALF_PI,n=Math.abs(o)&lt;=t.EPSLN?this.lat0&gt;=0?t.adjust_lon(this.long0+Math.atan2(s.x,-s.y)):t.adjust_lon(this.long0-Math.atan2(-s.x,s.y)):t.adjust_lon(this.long0+Math.atan2(s.x*h,i*this.cos_p12*e-s.y*this.sin_p12*h))),s.x=n,s.y=r,s}return l=t.e0fn(this.es),u=t.e1fn(this.es),c=t.e2fn(this.es),M=t.e3fn(this.es),Math.abs(this.sin_p12-1)&lt;=t.EPSLN?(f=this.a*t.mlfn(l,u,c,M,t.HALF_PI),i=Math.sqrt(s.x*s.x+s.y*s.y),m=f-i,r=t.imlfn(m/this.a,l,u,c,M),n=t.adjust_lon(this.long0+Math.atan2(s.x,-1*s.y)),s.x=n,s.y=r,s):Math.abs(this.sin_p12+1)&lt;=t.EPSLN?(f=this.a*t.mlfn(l,u,c,M,t.HALF_PI),i=Math.sqrt(s.x*s.x+s.y*s.y),m=i-f,r=t.imlfn(m/this.a,l,u,c,M),n=t.adjust_lon(this.long0+Math.atan2(s.x,s.y)),s.x=n,s.y=r,s):(i=Math.sqrt(s.x*s.x+s.y*s.y),d=Math.atan2(s.x,s.y),p=t.gN(this.a,this.e,this.sin_p12),y=Math.cos(d),g=this.e*this.cos_p12*y,x=-g*g/(1-this.es),v=3*this.es*(1-x)*this.sin_p12*this.cos_p12*y/(1-this.es),P=i/p,b=P-x*(1+x)*Math.pow(P,3)/6-v*(1+3*x)*Math.pow(P,4)/24,C=1-x*b*b/2-P*b*b*b/6,_=Math.asin(this.sin_p12*Math.cos(b)+this.cos_p12*Math.sin(b)*y),n=t.adjust_lon(this.long0+Math.asin(Math.sin(d)*Math.sin(b)/Math.cos(_))),r=Math.atan((1-this.es*C*this.sin_p12/Math.sin(_))*Math.tan(_)/(1-this.es)),s.x=n,s.y=r,s)</td>
      </tr>
      <tr>
        <td id="L3" class="blob-num js-line-number" data-line-number="3"></td>
        <td id="LC3" class="blob-code blob-code-inner js-file-line">}}}),i(&quot;proj4/projections&quot;,[&quot;require&quot;,&quot;exports&quot;,&quot;module&quot;,&quot;./projCode/longlat&quot;,&quot;./projCode/tmerc&quot;,&quot;./projCode/utm&quot;,&quot;./projCode/sterea&quot;,&quot;./projCode/somerc&quot;,&quot;./projCode/omerc&quot;,&quot;./projCode/lcc&quot;,&quot;./projCode/krovak&quot;,&quot;./projCode/cass&quot;,&quot;./projCode/laea&quot;,&quot;./projCode/merc&quot;,&quot;./projCode/aea&quot;,&quot;./projCode/gnom&quot;,&quot;./projCode/cea&quot;,&quot;./projCode/eqc&quot;,&quot;./projCode/poly&quot;,&quot;./projCode/nzmg&quot;,&quot;./projCode/mill&quot;,&quot;./projCode/sinu&quot;,&quot;./projCode/moll&quot;,&quot;./projCode/eqdc&quot;,&quot;./projCode/vandg&quot;,&quot;./projCode/aeqd&quot;,&quot;./projCode/longlat&quot;],function(t,s){s.longlat=t(&quot;./projCode/longlat&quot;),s.identity=s.longlat,s.tmerc=t(&quot;./projCode/tmerc&quot;),s.utm=t(&quot;./projCode/utm&quot;),s.sterea=t(&quot;./projCode/sterea&quot;),s.somerc=t(&quot;./projCode/somerc&quot;),s.omerc=t(&quot;./projCode/omerc&quot;),s.lcc=t(&quot;./projCode/lcc&quot;),s.krovak=t(&quot;./projCode/krovak&quot;),s.cass=t(&quot;./projCode/cass&quot;),s.laea=t(&quot;./projCode/laea&quot;),s.merc=t(&quot;./projCode/merc&quot;),s.aea=t(&quot;./projCode/aea&quot;),s.gnom=t(&quot;./projCode/gnom&quot;),s.cea=t(&quot;./projCode/cea&quot;),s.eqc=t(&quot;./projCode/eqc&quot;),s.poly=t(&quot;./projCode/poly&quot;),s.nzmg=t(&quot;./projCode/nzmg&quot;),s.mill=t(&quot;./projCode/mill&quot;),s.sinu=t(&quot;./projCode/sinu&quot;),s.moll=t(&quot;./projCode/moll&quot;),s.eqdc=t(&quot;./projCode/eqdc&quot;),s.vandg=t(&quot;./projCode/vandg&quot;),s.aeqd=t(&quot;./projCode/aeqd&quot;),s.longlat=t(&quot;./projCode/longlat&quot;),s.identity=s.longlat}),i(&quot;proj4/Proj&quot;,[&quot;./extend&quot;,&quot;./common&quot;,&quot;./defs&quot;,&quot;./constants&quot;,&quot;./datum&quot;,&quot;./projections&quot;,&quot;./wkt&quot;,&quot;./projString&quot;],function(t,s,i,a,h,e,n,r){var o=function l(s){if(!(this instanceof l))return new l(s);this.srsCodeInput=s;var a;&quot;string&quot;==typeof s?s in i?(this.deriveConstants(i[s]),t(this,i[s])):s.indexOf(&quot;GEOGCS&quot;)&gt;=0||s.indexOf(&quot;GEOCCS&quot;)&gt;=0||s.indexOf(&quot;PROJCS&quot;)&gt;=0||s.indexOf(&quot;LOCAL_CS&quot;)&gt;=0?(a=n(s),this.deriveConstants(a),t(this,a)):&quot;+&quot;===s[0]&amp;&amp;(a=r(s),this.deriveConstants(a),t(this,a)):(this.deriveConstants(s),t(this,s)),this.initTransforms(this.projName)};return o.prototype={initTransforms:function(s){if(!(s in o.projections))throw&quot;unknown projection &quot;+s;t(this,o.projections[s]),this.init()},deriveConstants:function(i){if(i.nadgrids&amp;&amp;0===i.nadgrids.length&amp;&amp;(i.nadgrids=null),i.nadgrids){i.grids=i.nadgrids.split(&quot;,&quot;);var e=null,n=i.grids.length;if(n&gt;0)for(var r=0;n&gt;r;r++){e=i.grids[r];var o=e.split(&quot;@&quot;);&quot;&quot;!==o[o.length-1]&amp;&amp;(i.grids[r]={mandatory:1===o.length,name:o[o.length-1],grid:a.grids[o[o.length-1]]},i.grids[r].mandatory&amp;&amp;!i.grids[r].grid)}}if(i.datumCode&amp;&amp;&quot;none&quot;!==i.datumCode){var l=a.Datum[i.datumCode];l&amp;&amp;(i.datum_params=l.towgs84?l.towgs84.split(&quot;,&quot;):null,i.ellps=l.ellipse,i.datumName=l.datumName?l.datumName:i.datumCode)}if(!i.a){var u=a.Ellipsoid[i.ellps]?a.Ellipsoid[i.ellps]:a.Ellipsoid.WGS84;t(i,u)}i.rf&amp;&amp;!i.b&amp;&amp;(i.b=(1-1/i.rf)*i.a),(0===i.rf||Math.abs(i.a-i.b)&lt;s.EPSLN)&amp;&amp;(i.sphere=!0,i.b=i.a),i.a2=i.a*i.a,i.b2=i.b*i.b,i.es=(i.a2-i.b2)/i.a2,i.e=Math.sqrt(i.es),i.R_A&amp;&amp;(i.a*=1-i.es*(s.SIXTH+i.es*(s.RA4+i.es*s.RA6)),i.a2=i.a*i.a,i.b2=i.b*i.b,i.es=0),i.ep2=(i.a2-i.b2)/i.b2,i.k0||(i.k0=1),i.axis||(i.axis=&quot;enu&quot;),i.datum=h(i)}},o.projections=e,o}),i(&quot;proj4/datum_transform&quot;,[&quot;./common&quot;],function(t){return function(s,i,a){function h(s){return s===t.PJD_3PARAM||s===t.PJD_7PARAM}var e,n,r;if(s.compare_datums(i))return a;if(s.datum_type===t.PJD_NODATUM||i.datum_type===t.PJD_NODATUM)return a;var o=s.a,l=s.es,u=i.a,c=i.es,M=s.datum_type;if(M===t.PJD_GRIDSHIFT)if(0===this.apply_gridshift(s,0,a))s.a=t.SRS_WGS84_SEMIMAJOR,s.es=t.SRS_WGS84_ESQUARED;else{if(!s.datum_params)return s.a=o,s.es=s.es,a;for(e=1,n=0,r=s.datum_params.length;r&gt;n;n++)e*=s.datum_params[n];if(0===e)return s.a=o,s.es=s.es,a;M=s.datum_params.length&gt;3?t.PJD_7PARAM:t.PJD_3PARAM}return i.datum_type===t.PJD_GRIDSHIFT&amp;&amp;(i.a=t.SRS_WGS84_SEMIMAJOR,i.es=t.SRS_WGS84_ESQUARED),(s.es!==i.es||s.a!==i.a||h(M)||h(i.datum_type))&amp;&amp;(s.geodetic_to_geocentric(a),h(s.datum_type)&amp;&amp;s.geocentric_to_wgs84(a),h(i.datum_type)&amp;&amp;i.geocentric_from_wgs84(a),i.geocentric_to_geodetic(a)),i.datum_type===t.PJD_GRIDSHIFT&amp;&amp;this.apply_gridshift(i,1,a),s.a=o,s.es=l,i.a=u,i.es=c,a}}),i(&quot;proj4/adjust_axis&quot;,[],function(){return function(t,s,i){var a,h,e,n=i.x,r=i.y,o=i.z||0;for(e=0;3&gt;e;e++)if(!s||2!==e||void 0!==i.z)switch(0===e?(a=n,h=&quot;x&quot;):1===e?(a=r,h=&quot;y&quot;):(a=o,h=&quot;z&quot;),t.axis[e]){case&quot;e&quot;:i[h]=a;break;case&quot;w&quot;:i[h]=-a;break;case&quot;n&quot;:i[h]=a;break;case&quot;s&quot;:i[h]=-a;break;case&quot;u&quot;:void 0!==i[h]&amp;&amp;(i.z=a);break;case&quot;d&quot;:void 0!==i[h]&amp;&amp;(i.z=-a);break;default:return null}return i}}),i(&quot;proj4/transform&quot;,[&quot;./common&quot;,&quot;./datum_transform&quot;,&quot;./adjust_axis&quot;,&quot;./Proj&quot;],function(t,s,i,a){return function(h,e,n){function r(s,i){return(s.datum.datum_type===t.PJD_3PARAM||s.datum.datum_type===t.PJD_7PARAM)&amp;&amp;&quot;WGS84&quot;!==i.datumCode}var o;return h.datum&amp;&amp;e.datum&amp;&amp;(r(h,e)||r(e,h))&amp;&amp;(o=new a(&quot;WGS84&quot;),this.transform(h,o,n),h=o),&quot;enu&quot;!==h.axis&amp;&amp;i(h,!1,n),&quot;longlat&quot;===h.projName?(n.x*=t.D2R,n.y*=t.D2R):(h.to_meter&amp;&amp;(n.x*=h.to_meter,n.y*=h.to_meter),h.inverse(n)),h.from_greenwich&amp;&amp;(n.x+=h.from_greenwich),n=s(h.datum,e.datum,n),e.from_greenwich&amp;&amp;(n.x-=e.from_greenwich),&quot;longlat&quot;===e.projName?(n.x*=t.R2D,n.y*=t.R2D):(e.forward(n),e.to_meter&amp;&amp;(n.x/=e.to_meter,n.y/=e.to_meter)),&quot;enu&quot;!==e.axis&amp;&amp;i(e,!0,n),n}}),i(&quot;proj4/core&quot;,[&quot;./Point&quot;,&quot;./Proj&quot;,&quot;./transform&quot;],function(t,s,i){var a=s(&quot;WGS84&quot;);return function(h,e,n){var r=function(s,a,n){var r;return Array.isArray(n)?(r=i(s,a,t(n)),3===n.length?[r.x,r.y,r.z]:[r.x,r.y]):i(h,e,n)};return h=h instanceof s?h:s(h),&quot;undefined&quot;==typeof e?(e=h,h=a):&quot;string&quot;==typeof e?e=s(e):&quot;x&quot;in e||Array.isArray(e)?(n=e,e=h,h=a):e=e instanceof s?e:s(e),n?r(h,e,n):{forward:function(t){return r(h,e,t)},inverse:function(t){return r(e,h,t)}}}}),i(&quot;proj4&quot;,[&quot;proj4/core&quot;,&quot;proj4/Proj&quot;,&quot;proj4/Point&quot;,&quot;proj4/defs&quot;,&quot;proj4/transform&quot;,&quot;proj4/mgrs&quot;],function(t,s,i,a,h,e){return t.defaultDatum=&quot;WGS84&quot;,t.Proj=s,t.WGS84=new t.Proj(&quot;WGS84&quot;),t.Point=i,t.defs=a,t.transform=h,t.mgrs=e,t}),s(&quot;proj4&quot;)});</td>
      </tr>
</table>

  </div>

</div>

<a href="#jump-to-line" rel="facebox[.linejump]" data-hotkey="l" style="display:none">Jump to Line</a>
<div id="jump-to-line" style="display:none">
  <form accept-charset="UTF-8" action="" class="js-jump-to-line-form" method="get"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /></div>
    <input class="linejump-input js-jump-to-line-field" type="text" placeholder="Jump to line&hellip;" autofocus>
    <button type="submit" class="btn">Go</button>
</form></div>

        </div>

      </div><!-- /.repo-container -->
      <div class="modal-backdrop"></div>
    </div><!-- /.container -->
  </div><!-- /.site -->


    </div><!-- /.wrapper -->

      <div class="container">
  <div class="site-footer" role="contentinfo">
    <ul class="site-footer-links right">
        <li><a href="https://status.github.com/" data-ga-click="Footer, go to status, text:status">Status</a></li>
      <li><a href="https://developer.github.com" data-ga-click="Footer, go to api, text:api">API</a></li>
      <li><a href="https://training.github.com" data-ga-click="Footer, go to training, text:training">Training</a></li>
      <li><a href="https://shop.github.com" data-ga-click="Footer, go to shop, text:shop">Shop</a></li>
        <li><a href="https://github.com/blog" data-ga-click="Footer, go to blog, text:blog">Blog</a></li>
        <li><a href="https://github.com/about" data-ga-click="Footer, go to about, text:about">About</a></li>
      <li><a href="https://help.github.com" data-ga-click="Footer, go to help, text:help">Help</a></li>

    </ul>

    <a href="https://github.com" aria-label="Homepage">
      <span class="mega-octicon octicon-mark-github" title="GitHub"></span>
</a>
    <ul class="site-footer-links">
      <li>&copy; 2015 <span title="0.04682s from github-fe139-cp1-prd.iad.github.net">GitHub</span>, Inc.</li>
        <li><a href="https://github.com/site/terms" data-ga-click="Footer, go to terms, text:terms">Terms</a></li>
        <li><a href="https://github.com/site/privacy" data-ga-click="Footer, go to privacy, text:privacy">Privacy</a></li>
        <li><a href="https://github.com/security" data-ga-click="Footer, go to security, text:security">Security</a></li>
        <li><a href="https://github.com/contact" data-ga-click="Footer, go to contact, text:contact">Contact</a></li>
    </ul>
  </div>
</div>


    <div class="fullscreen-overlay js-fullscreen-overlay" id="fullscreen_overlay">
  <div class="fullscreen-container js-suggester-container">
    <div class="textarea-wrap">
      <textarea name="fullscreen-contents" id="fullscreen-contents" class="fullscreen-contents js-fullscreen-contents" placeholder=""></textarea>
      <div class="suggester-container">
        <div class="suggester fullscreen-suggester js-suggester js-navigation-container"></div>
      </div>
    </div>
  </div>
  <div class="fullscreen-sidebar">
    <a href="#" class="exit-fullscreen js-exit-fullscreen tooltipped tooltipped-w" aria-label="Exit Zen Mode">
      <span class="mega-octicon octicon-screen-normal"></span>
    </a>
    <a href="#" class="theme-switcher js-theme-switcher tooltipped tooltipped-w"
      aria-label="Switch themes">
      <span class="octicon octicon-color-mode"></span>
    </a>
  </div>
</div>



    
    

    <div id="ajax-error-message" class="flash flash-error">
      <span class="octicon octicon-alert"></span>
      <a href="#" class="octicon octicon-x flash-close js-ajax-error-dismiss" aria-label="Dismiss error"></a>
      Something went wrong with that request. Please try again.
    </div>


      <script crossorigin="anonymous" src="https://assets-cdn.github.com/assets/frameworks-808fcfcd63c9ecba3e84453f540cb1cbafde48c6b30c1d51ebd4e67e88ff66bd.js"></script>
      <script async="async" crossorigin="anonymous" src="https://assets-cdn.github.com/assets/github/index-f66b416610dec6e8a6a3108cbbeaf5c55898e39d95710a3fd195522d322c9dba.js"></script>
      
      
  </body>
</html>

