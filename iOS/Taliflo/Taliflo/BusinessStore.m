//
//  TLFBusinessStore.m
//  taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "BusinessStore.h"

@interface BusinessStore ()

@end

@implementation BusinessStore

+ (instancetype)getInstance
{
    static BusinessStore *instance = nil;
    static dispatch_once_t pred;
    
    if (instance) return instance;
    
    dispatch_once(&pred, ^{
        instance = [BusinessStore alloc];
        instance = [instance initPrivate];
    });
    
    return instance;
}

// This method should not be called
- (instancetype)init
{
    @throw [NSException exceptionWithName:@"Singleton"
                                   reason:@"Use +[TLFCauseStore getInstance]"
                                 userInfo:nil];
}

- (instancetype)initPrivate
{
    return [super init];
}

@end
