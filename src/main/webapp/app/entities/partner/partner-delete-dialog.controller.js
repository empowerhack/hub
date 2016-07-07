(function() {
    'use strict';

    angular
        .module('hubApp')
        .controller('PartnerDeleteController',PartnerDeleteController);

    PartnerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Partner'];

    function PartnerDeleteController($uibModalInstance, entity, Partner) {
        var vm = this;

        vm.partner = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Partner.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
