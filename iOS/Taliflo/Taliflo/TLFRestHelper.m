//
//  TLFRestAssistant.m
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFRestHelper.h"
#import "AFNetworking.h"
#import "TLFCauseStore.h"
#import "TLFBusinessStore.h"

@implementation TLFRestHelper

static NSString *const base = @"http://api-dev.taliflo.com/v1/";

- (instancetype)initWithTableView:(UITableView *)tableView
{
    self = [super init];
    if (self) {
        _tableView = tableView;
    }
    return self;
}

- (NSURL *)queryUsers
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users"]];
}

- (NSURL *)queryTransactions
{
    return [NSURL URLWithString:[base stringByAppendingString:@"transactions"]];
}

- (NSURL *)queryUser:(int)numID
{
    NSMutableString *str = [[NSMutableString alloc] initWithString:base];
    [str appendString:@"users/"];
    [str appendFormat:@"%d", numID];
    return [NSURL URLWithString:str];
}

- (void)requestUsers:(NSString *)role
{
    NSURLRequest *request = [NSURLRequest requestWithURL:[self queryUsers]];
    
    // AFNetworking asynchronous URL request
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    [operation setCompletionBlockWithSuccess:
     ^(AFHTTPRequestOperation *operation, id responseObject) {
          NSLog(@"%@", responseObject[0]);
         
         [self sortUserResponse:responseObject byRole:role];
         
         dispatch_async(dispatch_get_main_queue(),
                        ^{
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

- (void)sortUserResponse:(id)responseObject byRole:(NSString *)role
{
    // Extract causes
    _users = [[NSMutableArray alloc] init];
    for (NSDictionary *obj in responseObject) {
        if ([obj[@"role"] isEqualToString:role])
            [_users addObject:obj];
    }
    
    NSLog(@"HERE %@", _users[0][@"company_name"]);
    
    // Sort alphabetically
    NSSortDescriptor *descriptor = [[NSSortDescriptor alloc]
                                    initWithKey:@"company_name"
                                    ascending:YES
                                    selector:@selector(localizedCaseInsensitiveCompare:)];
    
    [_users sortUsingDescriptors:@[descriptor]];
    
    // Add to global store
    //TODO: Add to global store
    if ([role isEqualToString:@"business"]) {
        TLFBusinessStore *bStore = [TLFBusinessStore getInstance];
        bStore.businesses = _users;
    }
    
    if ([role isEqualToString:@"cause"]) {
        TLFCauseStore *cStore = [TLFCauseStore getInstance];
        cStore.causes = _users;
    }
    
    NSLog(@"Asynchronous Request Complete");
}




@end
