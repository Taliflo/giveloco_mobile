//
//  TLFCauseStore.m
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFCauseStore.h"

@interface TLFCauseStore ()

@end

@implementation TLFCauseStore

+ (instancetype)getInstance
{
    static TLFCauseStore *instance = nil;
    static dispatch_once_t pred;
    
    if (instance) return instance;
    
    dispatch_once(&pred, ^{
        instance = [TLFCauseStore alloc];
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
