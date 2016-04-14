(function() {
    'use strict';

    angular
        .module('hubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('entity-audit', {
            parent: 'admin',
            url: '/entity-audits',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Audits'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/entity-audit/entity-audits.html',
                    controller: 'EntityAuditController',
                    controllerAs: 'vm'
                }
            },
            resolve: { }
        });
    }
})();
