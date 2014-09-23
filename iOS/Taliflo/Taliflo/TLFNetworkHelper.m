//
//  TLFRestAssistant.m
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFNetworkHelper.h"
#import "TLFCauseStore.h"
#import "TLFBusinessStore.h"
#import "TLFUserStore.h"
#import "TLFAlert.h"
#import "TLFUser.h"

@interface TLFNetworkHelper () 

@end

@implementation TLFNetworkHelper

static NSString *const base = @"http://api-dev.taliflo.com/";
static double startTime, endTime;

+ (void)jsonResponse:(NSURLRequest *)request successHandler:(void (^)(AFHTTPRequestOperation *operation, id responseObject))onSuccess failureHandler:(void (^)(AFHTTPRequestOperation *operation, NSError *error))onFailure
{
    // AFNetworking asynchronous request
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    [operation setCompletionBlockWithSuccess:onSuccess failure:onFailure];
    [operation start];
}

+ (void)requestUser:(NSString *)uid successHandler:(void (^)(AFHTTPRequestOperation *operation, id responseObject))onSuccess failureHandler:(void (^)(AFHTTPRequestOperation *operation, NSError *error))onFailure
{
    NSMutableString *urlString = [[NSMutableString alloc] initWithFormat:base];
    [urlString appendFormat:@"v1/users/%@", uid];
    NSURL *url = [NSURL URLWithString:[NSString stringWithString:urlString]];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    [TLFNetworkHelper jsonResponse:request successHandler:onSuccess failureHandler:onFailure];
}

+ (void)requestUsers:(NSString *)role forTableViewController:(UITableViewController *)viewController backingList:(NSMutableArray *)list
{
    startTime = CACurrentMediaTime();
    
    __block UIView *indicatorView = [[NSBundle mainBundle] loadNibNamed:@"ActivityIndicatorView" owner:viewController.tableView.superview options:nil][0];
    indicatorView.center = CGPointMake(160, 176);
    [viewController.tableView addSubview:indicatorView];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[base stringByAppendingString:@"v1/users"]]];
    
    void (^onSuccess)(AFHTTPRequestOperation *operation, id responseObject);
    void (^onFailure)(AFHTTPRequestOperation *operation, NSError *error);
    
    onSuccess = ^void(AFHTTPRequestOperation *operation, id responseObject) {
        [TLFNetworkHelper sortUserResponse:responseObject byRole:role forList:list];
        
        dispatch_async(dispatch_get_main_queue(),^{
                       [indicatorView removeFromSuperview];
                       [viewController.tableView reloadData];
                       endTime = CACurrentMediaTime();
                       NSLog(@"Request users [%@] execution time: %f sec", role, (endTime - startTime));
                       
                       if ([role isEqualToString:@"business"]) {
                           TLFUser *currentUser = [[TLFUserStore getInstance] currentUser];
                           [currentUser determineRedeemableBusinesses];
                           NSLog(@"Total redeemable businesses: %lu", (unsigned long)[currentUser.redeemableBusinesses count]);
                       }
        });
    };
    
    onFailure = ^void(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Request Failed: %@", [error localizedDescription]);
        
        // Show alert
        NSString *title = [NSString stringWithFormat:@"%@ list retrieval error", role];
        [TLFAlert alertForViewController:viewController forError:error withTitle:[title capitalizedString]];
    };
    
    [TLFNetworkHelper jsonResponse:request successHandler:onSuccess failureHandler:onFailure];
}

+ (void)sortUserResponse:(id)responseObject byRole:(NSString *)role forList:(NSMutableArray *)list
{
    // Extract users based on role
    for (NSDictionary *obj in responseObject) {
        if ([obj[@"role"] isEqualToString:role]) {
            [list addObject:obj];
        }
    }
    
    NSLog(@"FIRST OBJECT: %@, %@", list[0][@"company_name"], list[0][@"role"]);
    
    // Sort alphabetically
    NSSortDescriptor *descriptor = [[NSSortDescriptor alloc]
                                    initWithKey:@"company_name"
                                    ascending:YES
                                    selector:@selector(localizedCaseInsensitiveCompare:)];
    
    [list sortUsingDescriptors:@[descriptor]];
    
    // Add to global store
    if ([role isEqualToString:@"business"]) {
        TLFBusinessStore *bStore = [TLFBusinessStore getInstance];
        bStore.businesses = list;
    }
    
    if ([role isEqualToString:@"cause"]) {
        TLFCauseStore *cStore = [TLFCauseStore getInstance];
        cStore.causes = list;
    }
    
    NSLog(@"Asynchronous Request Complete");
}

+ (AFHTTPSessionManager *)newSessionManager:(NSString *)authToken
{
    AFHTTPSessionManager *manager = [[AFHTTPSessionManager alloc] initWithBaseURL:[NSURL URLWithString:base]];
    manager.requestSerializer = [AFJSONRequestSerializer serializer];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [manager.requestSerializer setValue:@"application/json" forHTTPHeaderField:@"Content-type"];
    [manager.requestSerializer setValue:@"application/json" forHTTPHeaderField:@"Accept"];
    
    if (authToken != nil) {
        [manager.requestSerializer setValue:authToken forHTTPHeaderField:@"x-session-token"];
    }
    
    return manager;
}

@end
