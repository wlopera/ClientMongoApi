// Modulo angular
// scope: Alcance de variables 
// hhtp: libreria HTPP consulta de servicios REST
angular.module("MyApp", [])
    .controller("MyController",["$scope", "$http", function($scope, $http){
    	
    	$scope.showData = false; 	// Variable para mostrar resultados
    	$scope.clients = {};     	// Clientes encontrados en la consulta
    	$scope.record = {};     	// Clientes actual
    	
    	// Permite consultar clientes - protocolo HTTP
    	$scope.getCustomer = function(){
    	  $http.get("/client/findAll")        // URI de llamada
    	  .then(function onSuccess(response) {     // Respuesta OK
    		$scope.showData = true;
    	    $scope.clients=response.data;
    	    console.log("##=> clientes: ", $scope.clients);
    	  }).catch(function onError(response) {   // Respuesta Error
    		$scope.showData = false;
    	    console.log("##=> Error: ", response);
    	  });
    	  $('#recordModal').modal('show');
    	};

    	// Permite agregar un cliente - protocolo HTTP
    	$scope.addCustomer = function(){
    	  // URI de llamada
    	   $http({
    		   url: "/client/save",
    		   method: "POST",
    		   data: JSON.stringify($scope.record),
    		   headers: {"Content-Type": "application/json"}
    	   })
    	  .then(function onSuccess(response) {     // Respuesta OK
    		$scope.showData = true;
    	    $scope.clients=response.data;
    	    console.log("##=> clientes: ", $scope.clients);
    	  }).catch(function onError(response) {   // Respuesta Error
    		$scope.showData = false;
    	    console.log("##=> Error: ", response);
    	  });
    	   $scope.record = {};
    	  $('#recordModal').modal('hide');
    	};

    	    	 
      /**
       * Permite mostrar la ventana modal para ingresar datos a procesar
       */
       $scope.showModal = function(){
    	  $('#recordModal').modal('show');
       }
    	
    }]);