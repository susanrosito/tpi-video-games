package ar.com.unq.tpi;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.normal.DefaultWorldPool;

public class PhysicsWorld {
    public int targetFPS = 40;
    public int timeStep = (1000 / targetFPS);
    public int iterations = 5;

    private Body[] bodies;
    private int count = 0;

    private AABB worldAABB;
    private World world;
    private BodyDef groundBodyDef;

    public void create() {
        // Step 1: Create Physics World Boundaries
        worldAABB = new AABB();
        worldAABB.lowerBound.set(new Vec2((float) -100.0, (float) -100.0));
        worldAABB.upperBound.set(new Vec2((float) 100.0, (float) 100.0));

        // Step 2: Create Physics World with Gravity
        Vec2 gravity = new Vec2((float) 0.0, (float) -10.0);
        boolean doSleep = true;
        world = new World(gravity, doSleep, new DefaultWorldPool(20, 20));

        // Step 3: Create Ground Box
        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vec2(0.0F, -10.0F));
        Body groundBody = world.createBody(groundBodyDef);
    }

    public void addBall() {
        // Create Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((float) 6.0+count, (float) 24.0);
        bodies[count] = world.createBody(bodyDef);

        // Create Shape with Properties

        // Increase Counter
        count += 1;
    }

    public void update() {
        // Update Physics World
        world.step(timeStep, 1 , iterations);

        // Print info of latest body
        if (count > 0) {
            Vec2 position = bodies[count].getPosition();
            float angle = bodies[count].getAngle();
        }
    }
}