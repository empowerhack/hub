(function() {
    'use strict';

    angular
        .module('hubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tag', {
            parent: 'entity',
            url: '/tag?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hubApp.tag.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tag/tags.html',
                    controller: 'TagController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tag');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tag-detail', {
            parent: 'entity',
            url: '/tag/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hubApp.tag.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tag/tag-detail.html',
                    controller: 'TagDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tag');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tag', function($stateParams, Tag) {
                    return Tag.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('tag.new', {
            parent: 'tag',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tag/tag-dialog.html',
                    controller: 'TagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tag', null, { reload: true });
                }, function() {
                    $state.go('tag');
                });
            }]
        })
        .state('tag.edit', {
            parent: 'tag',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tag/tag-dialog.html',
                    controller: 'TagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tag', function(Tag) {
                            return Tag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tag', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tag.delete', {
            parent: 'tag',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tag/tag-delete-dialog.html',
                    controller: 'TagDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tag', function(Tag) {
                            return Tag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tag', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
