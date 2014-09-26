//
//  TLFUserStore.m
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#define k_loggedIn @"loggedIn"
#define k_authToken @"authToken"
#define k_uid @"uid"

#import "TLFUserStore.h"

@interface TLFUserStore ()

@end

@implementation TLFUserStore

+ (instancetype)getInstance
{
    static TLFUserStore *instance = nil;
    static dispatch_once_t pred;
    
    if (instance) return instance;
    
    dispatch_once(&pred, ^{
        instance = [TLFUserStore alloc];
        instance = [instance initPrivate];
    });
    
    return instance;
}

// This method should not be called
- (instancetype)init
{
    @throw [NSException exceptionWithName:@"Singleton"
                                   reason:@"Use +[TLFUserStore getInstance]"
                                 userInfo:nil];
}

- (instancetype)initPrivate
{
    return [super init];
}

- (void)setLoggedInCredentials:(NSDictionary *)credentials
{
    self.authToken = credentials[@"auth_token"];
    self.uid = credentials[@"uid"];

    // Save the credentials
    NSUserDefaults *savedCredentials = [NSUserDefaults standardUserDefaults];
    [savedCredentials setBool:TRUE forKey:k_loggedIn];
    [savedCredentials setObject:[self.authToken copy] forKey:k_authToken];
    [savedCredentials setObject:[self.uid copy] forKey:k_uid];
    [savedCredentials synchronize];
}

@end
