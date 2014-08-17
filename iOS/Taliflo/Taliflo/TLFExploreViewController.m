//
//  TLFExploreViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/16/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFExploreViewController.h"
#import "TLFNavBarConfig.h"
#import "TLFColor.h"
#import "AFNetworking.h"
#import "TLFRestAPI.h"

@interface TLFExploreViewController ()

@property (nonatomic, strong) IBOutlet UISegmentedControl *segmentedControl;
@property (nonatomic, copy) NSMutableArray *businesses, *causes;
@property (nonatomic) NSArray *data;

@end

@implementation TLFExploreViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        [TLFNavBarConfig configViewController:self
                                    withTitle:@"Explore"
                                    withImage:[UIImage imageNamed:@"Explore.png"]];
        
        NSArray *segmentOptions = @[@"Businesses", @"Causes"];
        _segmentedControl = [[UISegmentedControl alloc] initWithItems:segmentOptions];
        _segmentedControl.selectedSegmentIndex = 0;
        [_segmentedControl addTarget:self action:@selector(segmentSelection) forControlEvents:UIControlEventValueChanged];
        self.navigationItem.titleView = _segmentedControl;
        
        [self requestUsers];
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    
    // Setting the navigation bar style
    [TLFNavBarConfig configViewController:self withBarTintColor:[UIColor whiteColor] withTintColor:[TLFColor talifloTiffanyBlue]];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)requestUsers
{
    NSURLRequest *request = [NSURLRequest requestWithURL:[TLFRestAPI queryAllUsers]];
    
    // AFNetworking asynchronous url request
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    [operation setCompletionBlockWithSuccess:
     ^(AFHTTPRequestOperation *operation, id responseObject) {
         self.data = responseObject;
         NSDictionary *first = self.data[0];
         NSLog(@"%@", first);
         
         dispatch_async(dispatch_get_main_queue(),
                        ^{
                            [self sortData];
                            [self.tableView reloadData];
                        });
     }
     
                                     failure:
     ^(AFHTTPRequestOperation *operation, NSError *error) {
         // Handle error
         NSLog(@"Request Failed: %@, %@", error, error.userInfo);
     }];
    
    [operation start];
}

- (void)sortData
{
    _businesses = [[NSMutableArray alloc] init];
    _causes = [[NSMutableArray alloc] init];
    
    for (NSDictionary *obj in _data) {
        if ([obj[@"role"] isEqualToString:@"business"])
            [_businesses addObject:obj];
        else if ([obj[@"role"] isEqualToString:@"cause"])
            [_causes addObject:obj];
    }
}

- (IBAction)segmentSelection
{
    [self.tableView reloadData];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    switch (_segmentedControl.selectedSegmentIndex) {
        case 0:
            return [_businesses count];
            break;
        case 1:
            return [_causes count];
            break;
            
        default:
            return [_businesses count];
            break;
    }
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"cellIdentifier";
    UITableViewCell *cell = [self.tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    
    NSString *cellValue;
    switch (_segmentedControl.selectedSegmentIndex) {
        case 0:
            cellValue = _businesses[indexPath.row][@"company_name"];
            break;
        case 1:
            cellValue = _causes[indexPath.row][@"company_name"];
            break;
            
        default:
            cellValue = _businesses[indexPath.row][@"company_name"];
            break;
    }
    cell.textLabel.text = cellValue;
    return cell;
}


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
