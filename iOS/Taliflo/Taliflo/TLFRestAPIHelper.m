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
    static TLFRestAPIHelper *instance = nil;
    static dispatch_once_t pred;
    
    if (instance) return instance;
    
    dispatch_once(&pred, ^{
        instance = [TLFRestAPIHelper alloc];
        instance = [instance initPrivate];
    });
    
    return instance;
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

- (NSURL *)queryUsers
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users"]];
}

- (void)sortUsers:(NSArray *)data dest:(NSMutableArray *)destArray byRole:(NSString *)role
{
    destArray = [[NSMutableArray alloc] init];
    
    for (NSDictionary *obj in data) {
        if ([obj[@"role"] isEqualToString:role])
            [destArray addObject:obj];
    }
}



@end
