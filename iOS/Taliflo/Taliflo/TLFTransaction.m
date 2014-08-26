//
//  TLFTransaction.m
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFTransaction.h"

@interface TLFTransaction ()

@end

@implementation TLFTransaction

static NSDateFormatter *_dateFormatter;

- (instancetype)init
{
    self = [super init];
    
    if (_dateFormatter == nil) {
        _dateFormatter = [[NSDateFormatter alloc] init];
        _dateFormatter.dateStyle = NSDateFormatterShortStyle;
        _dateFormatter.timeStyle = NSDateFormatterNoStyle;
    }
    
    return self;
}

- (NSString *)formatCancelledAt
{
    return [_dateFormatter stringFromDate:_cancelledAt];
}

- (NSString *)formatCompletedAt
{
    return [_dateFormatter stringFromDate:_completedAt];
}

- (NSString *)formatCreatedAt
{
    return [_dateFormatter stringFromDate:_createdAt];
}

- (NSString *)formateUpdatedAt
{
    return [_dateFormatter stringFromDate:_updatedAt];
}

@end
