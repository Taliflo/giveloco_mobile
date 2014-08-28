//
//  TLFBusinessesViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFBusinessesViewController.h"
#import "TLFNavBarHelper.h"
#import "TLFColor.h"
#import "AFNetworking.h"
#import "TLFUserCell.h"
#import "TLFBusinessStore.h"
#import "TLFRestHelper.h"

@interface TLFBusinessesViewController ()

@property (nonatomic, strong) NSMutableArray *businesses;

@end

static TLFNavBarHelper *helper;
static TLFBusinessStore *businessStore;
static TLFRestHelper *restHelper;

@implementation TLFBusinessesViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        
        // Setting the nav bar title, and the tab bar title and image
        helper = [TLFNavBarHelper getInstance];
        [helper configViewController:self withTitle:@"Businesses" withImage:[UIImage imageNamed:@"Businesses.png"]];
        
        // Request businesses
        restHelper = [[TLFRestHelper alloc] initWithTableView:self.tableView];
        [restHelper requestUsers:@"business"];
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    
    // Setting the nav bar style
    [helper configViewController:self withBarTintColor:[UIColor whiteColor] withTintColor:[TLFColor talifloTiffanyBlue]];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    // Load the cell nib file
    UINib *nib = [UINib nibWithNibName:@"TLFUserCell" bundle:nil];
    // Register the nib file which contains the cell
    [self.tableView registerNib:nib forCellReuseIdentifier:@"TLFUserCell"];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return [restHelper.users count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 161.0f;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"TLFUserCell";
    
    TLFUserCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier forIndexPath:indexPath];
    
    if (!cell) {
        cell = [[TLFUserCell alloc] init];
    }
    
    cell.name.text = restHelper.users[indexPath.row][@"company_name"];
    cell.image.image = [UIImage imageNamed:@"160.gif"];
    cell.backgroundColor = [TLFColor talifloPurple];
    
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
