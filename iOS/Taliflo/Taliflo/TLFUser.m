//
//  TLFUser.m
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFUser.h"

@implementation TLFUser

- (instancetype)initWithDictionary:(NSDictionary *)dict
{
    self = [super init];
    
    return self;
}

- (int)getSupportedCausesCount
{
    return (int)_supportedCauses.count;
}

- (int)getSupportersCount
{
    return (int)_supporters.count;
}

- (NSString *)getTagsString
{
    NSMutableString *string = [[NSMutableString alloc] init];
    for (NSString *tag in _tags) {
        [string appendString:@"#"];
        [string appendString:tag];
        [string appendString:@" "];
    }
    return string;
}

@end
