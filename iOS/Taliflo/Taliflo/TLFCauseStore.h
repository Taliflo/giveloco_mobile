//
//  TLFCauseStore.h
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFCauseStore : NSObject

@property (nonatomic, strong) NSMutableArray* causes;

+ (instancetype)getInstance;

@end