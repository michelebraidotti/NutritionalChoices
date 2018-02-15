package my.project.services;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/nutritionalchoices-repo-test-context.xml",
        "classpath:/nutritionalchoices-services-test-context.xml"})
@Transactional
@Ignore
public class BasicTest {

}
