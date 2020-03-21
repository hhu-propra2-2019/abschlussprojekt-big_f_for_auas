package mops.adapters;

import mops.adapters.dtos.PublicationDto;
import mops.application.services.GroupService;
import mops.application.services.UserService;
import org.springframework.binding.message.MessageContext;
import org.springframework.core.env.Environment;

//@Service
//@PropertySource(value = "classpath:errormappings/datepollmappings.properties", encoding = "UTF-8")
public class PublicationAdapter {

    private final transient Environment errorEnvironment;
    private final transient GroupService groupService;
    private final transient UserService userService;

    //@Autowired
    public PublicationAdapter(Environment env, GroupService groupService, UserService userService) {
        this.errorEnvironment = env;
        this.groupService = groupService;
        this.userService = userService;
    }

    public boolean validate(PublicationDto publicationDto, MessageContext context) {
        return true;
    }
}
