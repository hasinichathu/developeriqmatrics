@RestController
@RequestMapping("/github")
public class RedDatabaseController {

    @Autowired
    private GitHubService gitHubService;

    
    @GetMapping("/all")
    public List<Item> getAllItems() {
        return dynamoDBService.getAllItems();
    }
}