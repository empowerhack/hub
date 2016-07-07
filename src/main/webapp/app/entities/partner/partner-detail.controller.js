(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('PartnerDetailController', PartnerDetailController);

    PartnerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Partner', 'Project'];

    function PartnerDetailController($scope, $rootScope, $stateParams, entity, Partner, Project) {
        var vm = this;

        vm.partner = entity;

        var unsubscribe = $rootScope.$on('hubApp:partnerUpdate', function(event, result) {
            vm.partner = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
