//
//  TLFMyAccountViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>

#import "TLFMyAccountViewController.h"
#import "TLFNavBarHelper.h"
#import "TLFColor.h"
#import "TLFRestHelper.h"
#import "AFNetworking.h"
#import "TLFUserStore.h"
#import "TLFTransactionCell.h"
#import "TLFTransaction.h"
#import "TLFSearchViewController.h"

@interface TLFMyAccountViewController ()

@property (nonatomic, strong) TLFUser *user;
@property (nonatomic, strong) NSMutableArray *transactions;

@end

static TLFNavBarHelper *helper;
static TLFRestHelper *restHelper;

@implementation TLFMyAccountViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        // Setting the nav bar title, and the tab bar title and image
        helper = [TLFNavBarHelper getInstance];
        [helper configViewController:self withTitle:@"My Account" withImage:[UIImage imageNamed:@"MyAccount.png"]];
        [self.navigationItem.rightBarButtonItem setAction:@selector(openBillingInfo:)];
        
        restHelper = [[TLFRestHelper alloc] init];
        [self requestUser:4];
    }
    return self;
}

- (IBAction)openBillingInfo:(id)sender
{
/*    TLFBillingInfoViewController *billingInfoVC = [[TLFBillingInfoViewController alloc] init];
    
    [self.navigationController pushViewController:billingInfoVC animated:YES]; */
    
    TLFSearchViewController *searchVC = [[TLFSearchViewController alloc] init];
    [self.navigationController pushViewController:searchVC animated:YES];
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
    // Do any additional setup after loading the view from its nib.
    
    // Styling
    [TLFColor setStrokeTB:_balance];
    [[_balance layer] setCornerRadius:3];
    
    // Load the cell nib file
    UINib *nib = [UINib nibWithNibName:@"TLFTransactionCell" bundle:nil];
    // Register the nib file which contains the cell
    [self.tableView registerNib:nib forCellReuseIdentifier:@"TLFTransactionCell"];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)requestUser:(int)numID
{
    NSURLRequest *request = [NSURLRequest requestWithURL:[restHelper queryUser:numID]];
    
    // AFNetworking asynchronous URL request
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    [operation setCompletionBlockWithSuccess:
     ^(AFHTTPRequestOperation *operation, id responseObject) {
         _user = [[TLFUser alloc] initWithDictionary:responseObject];
         
         NSLog(@"USER NAME: %@", _user.companyName);
         dispatch_async(dispatch_get_main_queue(),
                        ^{
                            // Set user in global store
                            TLFUserStore *userStore = [TLFUserStore getInstance];
                            userStore.currentUser = _user;
                            
                            // Populate layout views
                            _name.text = _user.companyName;
                            _balance.text = [NSString stringWithFormat:@"%@", _user.balance];
                            
                            [_tableView reloadData];
                        }
                        );
     }
                                     failure:
     ^(AFHTTPRequestOperation *operation, NSError *error) {
         // Handle error
         NSLog(@"Request Failed: %@, %@", error, error.userInfo);
     }
     ];
    
    [operation start];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_user.transactions count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 80.0f;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdent = @"TLFTransactionCell";
    
    TLFTransactionCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdent];
    
    if (!cell) {
        cell = [[TLFTransactionCell alloc] init];
    }

    TLFTransaction *trans = [[TLFTransaction alloc] initWithDictionary:_user.transactions[indexPath.row]];
    cell.party.text = trans.toName;
    cell.date.text = [trans formatCreatedAt];
    cell.type.text = trans.transType;
    cell.amount.text = [NSString stringWithFormat:@"C %@", trans.amount];
    cell.transID.text = [NSString stringWithFormat:@"%@", trans.transID];
    
    if (indexPath.row % 2 == 0)
        cell.backgroundColor = [TLFColor colorWithHexString:@"ffffff"];
    else
        cell.backgroundColor = [TLFColor colorWithHexString:@"eeeeee"];
    
    return cell;
}

@end
