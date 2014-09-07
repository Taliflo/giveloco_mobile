//
//  TLFUserSupportViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFUserSupportViewController.h"
#import "TLFNavBarHelper.h"
#import "TLFUserCell.h"
#import "TLFColor.h"
#import "TLFNavBarHelper.h"

@interface TLFUserSupportViewController ()

@end

static TLFNavBarHelper *nbHelper;
static NSString *cellName = @"TLFUserCell";
static TLFNavBarHelper *nbHelper;

@implementation TLFUserSupportViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        nbHelper = [[TLFNavBarHelper alloc] initWithViewController:self];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    // Load the cell nib file
    UINib *nib = [UINib nibWithNibName:cellName bundle:nil];
    // Register the nib file which contains the cell
    [self.tableView registerNib:nib forCellReuseIdentifier:cellName];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    if (nbHelper.actionMenu.isOpen)
        [nbHelper.actionMenu close];
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
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [_support count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 161.0f;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TLFUserCell *cell = [tableView dequeueReusableCellWithIdentifier:cellName forIndexPath:indexPath];
    
    // Configure the cell...
    
    cell.name.text = self.support[indexPath.row][@"company_name"];
    cell.summary.text = self.support[indexPath.row][@"summary"];
    
    if ([self.supportRole isEqualToString:@"cause"])
        cell.backgroundColor = [TLFColor talifloTiffanyBlue];
    
    if ([self.supportRole isEqualToString:@"business"])
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
