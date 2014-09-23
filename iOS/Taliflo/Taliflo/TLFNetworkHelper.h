//
//  TLFRestAssistant.h
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AFNetworking/AFHTTPSessionManager.h>
#import <AFNetworking/AFHTTPRequestOperation.h>

@class TLFCauseStore, TLFBusinessStore;

@interface TLFNetworkHelper : NSObject

+ (void)requestUsers:(NSString *)role forTableViewController:(UITableViewController *)viewController backingList:(NSMutableArray *)list;

+ (void)requestUser:(NSString *)uid successHandler:(void (^)(AFHTTPRequestOperation *operation, id responseObject))onSuccess failureHandler:(void (^)(AFHTTPRequestOperation *operation, NSError *error))onFailure;

+ (AFHTTPSessionManager *)newSessionManager:(NSString *)authToken;


@end
