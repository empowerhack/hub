(function() {
    'use strict';

    angular
        .module('hubApp')
        .directive('jhSocial', jhSocial);

    jhSocial.$inject = ['$translatePartialLoader', '$translate', '$filter', 'SocialService'];

    function jhSocial($translatePartialLoader, $translate, $filter, SocialService) {
        var directive = {
            restrict: 'E',
            scope: {
                provider: '@ngProvider'
            },
            templateUrl: 'app/account/social/directive/social.html',
            link: linkFunc
        };

        return directive;

        /* private helper methods */

        function linkFunc(scope) {
            
            $translatePartialLoader.addPart('social');
            $translate.refresh();
            
            scope.label = $filter('capitalize')(scope.provider);
            scope.providerSetting = SocialService.getProviderSetting(scope.provider);
            scope.providerURL = SocialService.getProviderURL(scope.provider);
            scope.csrf = SocialService.getCSRF();
        }

    }
})();
