var scene, camera, renderer;

var WIDTH  = window.innerWidth;
var HEIGHT = window.innerHeight;

var SPEED = 0.01;

function init() {
    scene = new THREE.Scene();

    initMesh();
    initCamera();
    initLights();
    initRenderer();

    document.body.appendChild(renderer.domElement);
}

function initCamera() {
    //camera = new THREE.PerspectiveCamera( 50, window.innerWidth / window.innerHeight, .1, 200000 );
    camera = new THREE.PerspectiveCamera(45, WIDTH/HEIGHT, 1, 2000);
    //camera = new THREE.PerspectiveCamera( 45, SCREEN_WIDTH / SCREEN_HEIGHT, 1, 200000 );

    camera.position.set( (WIDTH/22), (HEIGHT/22), 0 );
    camera.lookAt(scene.position);
}


function initRenderer() {
    renderer = new THREE.WebGLRenderer({ antialias: true });
    renderer.setSize(WIDTH, HEIGHT);
}

function initLights() {
    var light = new THREE.AmbientLight(0xffffff);
    scene.add(light);
    var light2 = new THREE.PointLight(0xffffff);
      light.position.set(100,200,100);
      scene.add(light2);
}

var mesh = null;
function initMesh() {
var rotation_matrix;


    var loader = new THREE.JSONLoader();
    loader.load('/json/s1.json', function(geometry, materials) {
    //var texture = THREE.ImageUtils.loadTexture('textures/skin.png');
    var color = new THREE.Color("rgb(25, 0, 0)");
         mesh = new THREE.Mesh(geometry, new THREE.MeshPhongMaterial(materials));
         mesh1 = new THREE.Mesh(geometry, new THREE.MeshPhongMaterial(materials));
         mesh2 = new THREE.Mesh(geometry, new THREE.MeshPhongMaterial(materials));

        //

        //mesh.scale.x = mesh.scale.y = mesh.scale.z = 5;
        mesh.translation = THREE.GeometryUtils.center(geometry);

        //mesh1.scale.x = mesh.scale.y = mesh.scale.z = 5;
        //mesh1.translation = THREE.GeometryUtils.center(geometry);

        //mesh2.scale.x = mesh.scale.y = mesh.scale.z = 5;
        //mesh2.translation = THREE.GeometryUtils.center(geometry);

        //position! to get different lines, all three are aligned

        mesh.position.set = new THREE.Vector3(10, 10, 10);
        //mesh1.position.set = new THREE.Vector3(10, 10, 10);
        mesh2.position.set = new THREE.Vector3(10, 10, 10)
        mesh1.translateX(5);
        //mesh1.translateX(5);



        //mesh2.translateY(-10);

        //
        scene.add(mesh);
        scene.add(mesh1);


        //scene.add(mesh2);


        //mesh.rotation.set(Math.PI/2, Math.PI/4, Math.PI/4); // Set initial rotation
        //mesh.matrix.setRotationFromEuler(mesh.rotation); // Apply rotation to the object's matrix



    });
}

function rotateMesh() {
    if (!mesh) {
        return;
    }

    //mesh.rotation.x -= SPEED * 0.05;
    mesh.rotation.x += SPEED;

   // mesh.rotation.z -= SPEED * 0.05;

   //mesh1.rotation.x -= SPEED * 0.05;
    mesh1.rotation.x += SPEED;

    //mesh1.rotation.z -= SPEED * 0.05;
}


function render() {
    requestAnimationFrame(render);
    rotateMesh();
    //rotation_matrix = new THREE.Matrix4().setRotationX(.01); // Animated rotation will be in .01 radians along object's X axis
    //rotation_matrix.multiplySelf(mesh.matrix);
    //mesh.rotation.setRotationFromMatrix(rotation_matrix);


    renderer.render(scene, camera);
}

init();
render();
