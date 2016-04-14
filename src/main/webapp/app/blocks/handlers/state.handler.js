(function() {
    'use strict';

    angular
        .module('hubApp')
        .factory('stateHandler', stateHandler);

    stateHandler.$inject = ['$rootScope', '$state', '$translate', 'JhiLanguageService', 'translationHandler',
        'Auth', 'Principal', 'ENV', 'VERSION'];

    function stateHandler($rootScope, $state, $translate, JhiLanguageService, translationHandler,
        Auth, Principal, ENV, VERSION) {
        return {
            initialize: initialize
        };

        function initialize() {
            $rootScope.ENV = ENV;
            $rootScope.VERSION = VERSION;
            $rootScope.back = back;

            var stateChangeStart = $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
                $rootScope.toState = toState;
                $rootScope.toStateParams = toStateParams;

                if (Principal.isIdentityResolved()) {
                    Auth.authorize();
                }

                
                // Update the language
                JhiLanguageService.getCurrent().then(function (language) {
                    $translate.use(language);
                });
                
            });

            var stateChangeSuccess = $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
                var titleKey = 'global.title' ;

                // Remember previous state unless we've been redirected to login or we've just
                // reset the state memory after logout. If we're redirected to login, our
                // previousState is already set in the authExpiredInterceptor. If we're going
                // to login directly, we don't want to be sent to some previous state anyway
                if (!$rootScope.redirected && $rootScope.previousStateName) {
                    $rootScope.previousStateName = fromState.name;
                    $rootScope.previousStateParams = fromParams;
                }

                // Set the page title key to the one configured in state or use default one
                if (toState.data.pageTitle) {
                    titleKey = toState.data.pageTitle;
                }
                translationHandler.updateTitle(titleKey);
            });

            $rootScope.$on('$destroy', function () {
                if(angular.isDefined(stateChangeStart) && stateChangeStart !== null){
                    stateChangeStart();
                }
                if(angular.isDefined(stateChangeSuccess) && stateChangeSuccess !== null){
                    stateChangeSuccess();
                }
            });

            function back() {
                // If previous state is 'activate' or do not exist go to 'home'
                if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                    $state.go('home');
                } else {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            }
        }
    }
})();
