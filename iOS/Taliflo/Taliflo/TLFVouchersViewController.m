//
//  TLFVouchersViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFVouchersViewController.h"
#import "TLFNavBarConfig.h"
#import "TLFColor.h"
#import "TLFRestAPI.h"
#import "AFNetworking.h"

@interface TLFVouchersViewController ()

@property (nonatomic) NSURLSession *session;
@property (nonatomic, copy) NSArray *vouchers;

@end

@implementation TLFVouchersViewController


- (instancetype)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    
    if (self) {
        
        // Setting the title and tab bar title and image
        [TLFNavBarConfig configViewController:self
                                    withTitle:@"My Vouchers"
                                    withImage:[UIImage imageNamed:@"Voucher.png"]];
        
        NSURLSessionConfiguration *config = [NSURLSessionConfiguration defaultSessionConfiguration];
        _session = [NSURLSession sessionWithConfiguration:config delegate:nil delegateQueue:nil];
        
        [self requestVouchers];
    }
    
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    
    // Setting the navigation bar style
    [TLFNavBarConfig configViewController:self
                         withBarTintColor:[UIColor whiteColor]
                            withTintColor:[TLFColor talifloTiffanyBlue]];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Load the cell nib file
//    UINib *cellNib = [UINib nibWithNibName:@"TLFVoucherCell" bundle:nil];
    
    // Register the nib fil
//    [self.tableView registerNib:cellNib forCellReuseIdentifier:@"TLFVoucherCell"];
    
    [self.tableView registerClass:[UITableViewCell class] forCellReuseIdentifier:@"UITableViewCell"];
    
}

- (void)requestVouchers
{
 /*   NSURLRequest *req = [NSURLRequest requestWithURL:[TLFRestAPI queryVouchers]];
    NSURLSessionDataTask *dataTask = [self.session dataTaskWithRequest:req
                                                     completionHandler:^(NSData *data, NSURLResponse *response, NSError *error){
    // Apple has a built-in class for parsing JSON data - NSJSONSerialization.
    // Given JSON data, it will create instances of NSDictionary for every JSON object, NSArray for every JSON array,
    // NSString for every JSON string, and NSNumber for every JSON number.
                                                         self.vouchers = [NSJSONSerialization
                                                                          JSONObjectWithData:data
                                                                          options:0
                                                                          error:nil];
                                                         NSDictionary *first = self.vouchers[0];
                                                         NSLog(@"%@", first);
                                                         
                                                         dispatch_async(dispatch_get_main_queue(),
                                                                        ^{[self.tableView reloadData];
                                                                        });
                                                     }];
    
    // Start the web service request
    [dataTask resume]; */
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[TLFRestAPI queryVouchers]];
    // AFNetworking asynchronous url request
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    [operation setCompletionBlockWithSuccess:
     ^(AFHTTPRequestOperation *operation, id responseObject) {
         self.vouchers = responseObject;
         NSDictionary *first = self.vouchers[0];
         NSLog(@"%@", first);
         
         dispatch_async(dispatch_get_main_queue(),
                        ^{[self.tableView reloadData];
                        });
    }
     
    failure:
     ^(AFHTTPRequestOperation *operation, NSError *error) {
                                         // Handle error
                                         NSLog(@"Request Failed: %@, %@", error, error.userInfo);
    }];
    
    [operation start];
}

#pragma mark - Table view data source

// Return the number of sections.
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

// Return the number of rows in the section.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.vouchers count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIndentifier = @"UITableViewCell";
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIndentifier];
    NSDictionary *voucher = self.vouchers[indexPath.row];
    cell.textLabel.text = voucher[@"issued_by_name"];
    
    
    
    //cell.imageView.image =
    return cell;
}

@end
