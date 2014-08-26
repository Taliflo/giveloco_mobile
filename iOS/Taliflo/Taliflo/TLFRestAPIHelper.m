//
//  TLFRestAPIHelper.m
//  Taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFRestAPIHelper.h"
#import "AFNetworking.h"

static NSString *const base = @"http://api-dev.taliflo.com/v1/";

@implementation TLFRestAPIHelper

+ (instancetype)getInstance
{
    static TLFRestAPIHelper *sharedInstance = nil;
    static dispatch_once_t pred;
    
    if (sharedInstance) return sharedInstance;
    
    dispatch_once(&pred, ^{
        sharedInstance = [TLFRestAPIHelper alloc];
        sharedInstance = [sharedInstance initPrivate];
    });
    
    return sharedInstance;
}

// This method should not be called
- (instancetype)init
{
    @throw [NSException exceptionWithName:@"Singleton"
                                   reason:@"Use +[TLFRestAPIHelper getInstance]"
                                 userInfo:nil];
}

- (instancetype)initPrivate
{
    self = [super init];
    
    return self;
}

- (NSURL *)queryAllUsers
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users"]];
}

- (NSURL *)queryAllBusinesses
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users?role=business"]];
}

- (NSURL *)queryAllCauses
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users?role=cause"]];    
}

@end
